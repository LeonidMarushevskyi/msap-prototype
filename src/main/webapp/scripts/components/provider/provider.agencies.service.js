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
            findAgenciesWithinBox : function (box) {
               return HHSService.findFosterFamilyAgencies("$where=" + createWithinBoxClause(box));
            },
            findAgenciesByTextQuery : function (query) {
                query = "$q=" + query;
                return HHSService.findFosterFamilyAgencies(query);
            },
            findAgenciesByType : function (type) {
                return HHSService.findFosterFamilyAgencies("facility_type=" + type);
            },
            findAgenciesByStatus : function (status) {
                return HHSService.findFosterFamilyAgencies("facility_status=" + status);
            },
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
                return EntitySearchFacade.search(
                    {
                        $exactMatch: true,
                        '+providerName': filter.text,
                        '+providerType.name': filter.providerTypes,
                        '+qualityRating.name': filter.qualityRatings
                    },
                    // entity name
                    'Provider'
                ).then(function (searchResults) {
                    $log.debug(searchResults.entityName + ' query: ' + searchResults.stringQuery);
                    return searchResults.data;
                })
            }
        }
    });
