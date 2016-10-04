'use strict';

angular.module('msapApp')
    .controller('ScheduleDetailController', function ($scope, $rootScope, $stateParams, entity, Schedule) {
        $scope.schedule = entity;
        $scope.load = function (id) {
            Schedule.get({id: id}, function(result) {
                $scope.schedule = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:scheduleUpdate', function(event, result) {
            $scope.schedule = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
