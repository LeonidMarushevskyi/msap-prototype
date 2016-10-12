angular.module('msapApp')
    .factory('ProviderAgenciesService', function ($q, $log, EntitySearchFacade) {

        function searchOpenSlotsByAgeGroupsAndAvailableSpots(ageGroups) {
            return EntitySearchFacade.search(
                /*
                 this _.transform is to prepare the search query part like this:
                     (+ageGroup.code:2 +openSlots:>=3)
                     (+ageGroup.code:3)
                     (+ageGroup.code:5 +openSlots:>=1)
                 */
                _.transform(ageGroups, function (result, ageGroup) {
                    result.push(
                        {'':
                            [
                                {
                                    '+ageGroup.code': ageGroup.code
                                },
                                {
                                    $when: ageGroup.availableSpots,
                                    '+openSlots': '>=' + ageGroup.availableSpots
                                }
                            ]
                        }
                    );
                }, []),
                'OpenSlot'
            ).then(function (searchResults) {
                //$log.debug(searchResults.entityName + ' query: ' + searchResults.stringQuery);
                return searchResults.data;
            });
        }

        /*
         usage:
         ProviderAgenciesService.findAgenciesByFilter({
             bounds : {
                 northwest: {
                     latitude: 34.085175,
                     longitude: -117.67147
                 },
                 southeast: {
                     latitude: 34.085175,
                     longitude: -117.67147
                 }
             },
             text : "TERI",
             ...
         }).then(function (agencies) {
             $log.debug(agencies);
         });
         */
        function findAgenciesByFilter(filter) {
            var nw = filter.bounds.northwest;
            var se = filter.bounds.southeast;

            return $q.all({
                openSlots: searchOpenSlotsByAgeGroupsAndAvailableSpots(filter.ageGroups)
            }).then(function (data) {
                if (!_.isEmpty(filter.ageGroups) && _.isEmpty(data.openSlots)) {
                    return EntitySearchFacade.createSearchResults('-noOpenSlotsFoundByAgeGroups', 'OpenSlot', []);

                } else {
                    return EntitySearchFacade.search(
                        [
                            "*",
                            {
                                $anySuffix: true,
                                '+providerName': filter.text
                            },
                            {
                                '+address.longitude': {'[]': [nw.longitude, se.longitude]},
                                '+address.latitude': {'[]': [se.latitude, nw.latitude]},
                                '+providerType.code': filter.providerTypeCodes,
                                '+qualityRating.code': filter.qualityRatingCodes,
                                '+isBeforeSchool': filter.isBeforeSchool ? 'true' : '',
                                '+isAfterSchool': filter.isAfterSchool ? 'true' : '',
                                '+isFullDay': filter.isFullDay ? 'true' : '',
                                '+isWeekendCare': filter.isWeekendCare ? 'true' : '',
                                '+isOpenOvernight': filter.isOpenOvernight ? 'true' : '',
                                '+isSecondShift': filter.isSecondShift ? 'true' : '',
                                '+isRespiteCare': filter.isRespiteCare ? 'true' : '',

                                /*
                                 The following construct will prepare a query part like:
                                 +(
                                   openSlots.id:(1685 2385 ...)
                                   openSlots.id:(2686 3386 ...)
                                   ...
                                 )
                                 We need the _.chunk(..., 1024) workaround because of Elasticsearch limitations.
                                 */
                                '+': _.map(_.chunk(_.map(data.openSlots, 'id'), 1024), function (idListChunk) {
                                    return { 'openSlots.id': idListChunk };
                                }),

                                '+licenseType.code': filter.licenseTypeCodes,
                                '+supportedSpecialNeeds.specialNeedType.code': filter.specialNeedCodes,
                                '+supportedLanguages.language.code': filter.supportedLanguageCodes
                            },
                            {
                                $when: filter.isNoComplains,
                                '-numberOfComplains': '>0'
                            },
                            {
                                $when: filter.isNoAllegations,
                                '-substantiatedAllegations': '*'
                            }
                        ],
                        // entity name
                        'Provider',
                        // reusableOptions
                        null,
                        // pagination
                        null,
                        // usePOST
                        true
                    ).then(function (searchResults) {
                        $log.debug(searchResults.entityName + ' query: ' + searchResults.stringQuery);
                        return searchResults.data;
                    });
                }
            });
        }

        return {
            findAgenciesByFilter: findAgenciesByFilter
        };
    });
