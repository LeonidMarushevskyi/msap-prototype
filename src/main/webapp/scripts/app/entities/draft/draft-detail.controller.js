'use strict';

angular.module('msapApp')
    .controller('DraftDetailController', function ($scope, $rootScope, $stateParams, entity, Draft) {
        $scope.draft = entity;
        $scope.load = function (id) {
            Draft.get({id: id}, function(result) {
                $scope.draft = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:draftUpdate', function(event, result) {
            $scope.draft = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
