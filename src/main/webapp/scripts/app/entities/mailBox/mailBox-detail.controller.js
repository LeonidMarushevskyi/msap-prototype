'use strict';

angular.module('msapApp')
    .controller('MailBoxDetailController', function ($scope, $rootScope, $stateParams, entity, MailBox) {
        $scope.mailBox = entity;
        $scope.load = function (id) {
            MailBox.get({id: id}, function(result) {
                $scope.mailBox = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:mailBoxUpdate', function(event, result) {
            $scope.mailBox = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
