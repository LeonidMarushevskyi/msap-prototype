'use strict';

angular.module('msapApp')
    .controller('OutboxDetailController', function ($scope, $rootScope, $stateParams, entity, Outbox) {
        $scope.outbox = entity;
        $scope.load = function (id) {
            Outbox.get({id: id}, function(result) {
                $scope.outbox = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:outboxUpdate', function(event, result) {
            $scope.outbox = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
