'use strict';

angular.module('msapApp')
    .controller('ProviderDetailController', function ($scope, $rootScope, $stateParams, entity, Provider) {
        $scope.provider = entity;
        $scope.load = function (id) {
            Provider.get({id: id}, function(result) {
                $scope.provider = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:providerUpdate', function(event, result) {
            $scope.provider = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
