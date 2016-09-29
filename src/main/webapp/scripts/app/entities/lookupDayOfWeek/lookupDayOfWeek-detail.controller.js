'use strict';

angular.module('msapApp')
    .controller('LookupDayOfWeekDetailController', function ($scope, $rootScope, $stateParams, entity, LookupDayOfWeek) {
        $scope.lookupDayOfWeek = entity;
        $scope.load = function (id) {
            LookupDayOfWeek.get({id: id}, function(result) {
                $scope.lookupDayOfWeek = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupDayOfWeekUpdate', function(event, result) {
            $scope.lookupDayOfWeek = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
