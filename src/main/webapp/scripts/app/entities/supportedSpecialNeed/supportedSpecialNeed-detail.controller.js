'use strict';

angular.module('msapApp')
    .controller('SupportedSpecialNeedDetailController', function ($scope, $rootScope, $stateParams, entity, SupportedSpecialNeed) {
        $scope.supportedSpecialNeed = entity;
        $scope.load = function (id) {
            SupportedSpecialNeed.get({id: id}, function(result) {
                $scope.supportedSpecialNeed = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:supportedSpecialNeedUpdate', function(event, result) {
            $scope.supportedSpecialNeed = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
