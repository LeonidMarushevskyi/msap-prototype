'use strict';

angular.module('msapApp')
    .controller('LookupProviderTypeDetailController', function ($scope, $rootScope, $stateParams, entity, LookupProviderType) {
        $scope.lookupProviderType = entity;
        $scope.load = function (id) {
            LookupProviderType.get({id: id}, function(result) {
                $scope.lookupProviderType = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupProviderTypeUpdate', function(event, result) {
            $scope.lookupProviderType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
