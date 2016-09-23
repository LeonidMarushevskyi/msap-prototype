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

            controller: ['$scope', '$log', '$state', function ($scope, $log, $state) {

            }]
        }
    });
