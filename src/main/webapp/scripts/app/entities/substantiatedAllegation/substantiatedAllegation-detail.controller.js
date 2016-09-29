'use strict';

angular.module('msapApp')
    .controller('SubstantiatedAllegationDetailController', function ($scope, $rootScope, $stateParams, entity, SubstantiatedAllegation) {
        $scope.substantiatedAllegation = entity;
        $scope.load = function (id) {
            SubstantiatedAllegation.get({id: id}, function(result) {
                $scope.substantiatedAllegation = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:substantiatedAllegationUpdate', function(event, result) {
            $scope.substantiatedAllegation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
