'use strict';

angular.module('msapApp')
    .controller('LookupSpecialNeedTypeDetailController', function ($scope, $rootScope, $stateParams, entity, LookupSpecialNeedType) {
        $scope.lookupSpecialNeedType = entity;
        $scope.load = function (id) {
            LookupSpecialNeedType.get({id: id}, function(result) {
                $scope.lookupSpecialNeedType = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupSpecialNeedTypeUpdate', function(event, result) {
            $scope.lookupSpecialNeedType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
