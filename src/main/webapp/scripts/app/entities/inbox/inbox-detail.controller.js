'use strict';

angular.module('msapApp')
    .controller('InboxDetailController', function ($scope, $rootScope, $stateParams, entity, Inbox) {
        $scope.inbox = entity;
        $scope.load = function (id) {
            Inbox.get({id: id}, function(result) {
                $scope.inbox = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:inboxUpdate', function(event, result) {
            $scope.inbox = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
