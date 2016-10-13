'use strict';

angular.module('msapApp')
    .directive('apdqFacilityAgency', function () {
        return {
            restrict: 'E',
            scope: {
                agency: '=',
                viewConfig: '='
            },
            templateUrl: 'scripts/app/facilities/facilities-agency.html',

            controller: ['$scope', '$rootScope', '$log', '$state', 'leafletData', function ($scope, $rootScope, $log, $state, leafletData) {
                $scope.askAbout = function(agency) {
                    $log.debug('askAbout', agency);
                    $state.go('ch-inbox.new-mail', angular.merge($state.params, {askAbout: agency}));
                };

                $scope.zoomToMap = function(agency) {
                    $log.debug('zoomToMap = ', agency);
                    $rootScope.$broadcast('zoomToMap', {lat: agency.address.latitude, lng: agency.address.longitude, zoom: 18});
                };
            }]
        }
    });
