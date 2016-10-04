'use strict';

angular.module('msapApp')
    .controller('ScheduleController', function ($scope, $state, Schedule, ScheduleSearch) {

        $scope.schedules = [];
        $scope.loadAll = function() {
            Schedule.query(function(result) {
               $scope.schedules = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ScheduleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.schedules = result;
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
            $scope.schedule = {
                openHour: null,
                closeHour: null,
                id: null
            };
        };
    });
