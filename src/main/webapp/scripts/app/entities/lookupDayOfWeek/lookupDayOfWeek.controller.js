'use strict';

angular.module('msapApp')
    .controller('LookupDayOfWeekController', function ($scope, $state, LookupDayOfWeek, LookupDayOfWeekSearch) {

        $scope.lookupDayOfWeeks = [];
        $scope.loadAll = function() {
            LookupDayOfWeek.query(function(result) {
               $scope.lookupDayOfWeeks = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupDayOfWeekSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupDayOfWeeks = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lookupDayOfWeek = {
                code: null,
                name: null,
                id: null
            };
        };
    });
