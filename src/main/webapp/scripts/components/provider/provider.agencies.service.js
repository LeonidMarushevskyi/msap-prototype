/**
 * Created by dmitry.rudenko on 5/25/2016.
 */
angular.module('msapApp')
    .factory('ProviderAgenciesService', function ($log, EntitySearchFacade) {
        var joinValues = function (values) {
            var result = '';
            angular.forEach(values , function (value) {
                result= result.concat("'", value, "'", ",");
            });
            return result.slice(0, -1);
        };

        var createInClause = function (field, values) {
            if(!values || values.length === 0) {
                return " TRUE ";
            }
            var joinedValues = joinValues(values);
            return field + ' IN ( ' + joinedValues + ' ) ';
        };

        var createWithinBoxClause = function (box) {
            return "within_box(location, nw_lat, nw_long, se_lat, se_long)".
                    replace("nw_lat", box.northwest.latitude.toString()).
                    replace("nw_long", box.northwest.longitude.toString()).
                    replace("se_lat", box.southeast.latitude.toString()).
                    replace("se_long", box.southeast.longitude.toString());
        };

        return {
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
                    qualityRatings : ['Not Rated', 'Good'],
                    providerTypes : ['Non-Relative In-Home'],
                    text : "TERI"
                }).then(function (agencies) {
                    $log.debug(agencies);
                });
            */
            findAgenciesByFilter: function (filter) {
                var nw = filter.bounds.northwest;
                var se = filter.bounds.southeast;

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
                            '+providerType.code': filter.providerTypes,
                            '+qualityRating.code': filter.qualityRatings,
                            '+isBeforeSchool': filter.isBeforeSchool ? 'true' : '',
                            '+isAfterSchool': filter.isAfterSchool ? 'true' : '',
                            '+isFullDay': filter.isFullDay ? 'true' : '',
                            '+isWeekendCare': filter.isWeekendCare ? 'true' : '',
                            '+isOpenOvernight': filter.isOpenOvernight ? 'true' : '',
                            '+licenseType.code': filter.licenseTypes,
                            '+specialNeeds.code': filter.specialNeeds
                        }
                    ],
                    // entity name
                    'Provider'
                ).then(function (searchResults) {
                    $log.debug(searchResults.entityName + ' query: ' + searchResults.stringQuery);
                    return searchResults.data;
                })
            }
        }
    });
