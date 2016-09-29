'use strict';

angular.module('msapApp')
    .controller('DeletedDetailController', function ($scope, $rootScope, $stateParams, entity, Deleted) {
        $scope.deleted = entity;
        $scope.load = function (id) {
            Deleted.get({id: id}, function(result) {
                $scope.deleted = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:deletedUpdate', function(event, result) {
            $scope.deleted = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
