'use strict';

angular.module('msapApp')
    .controller('OpenSlotDetailController', function ($scope, $rootScope, $stateParams, entity, OpenSlot) {
        $scope.openSlot = entity;
        $scope.load = function (id) {
            OpenSlot.get({id: id}, function(result) {
                $scope.openSlot = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:openSlotUpdate', function(event, result) {
            $scope.openSlot = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
