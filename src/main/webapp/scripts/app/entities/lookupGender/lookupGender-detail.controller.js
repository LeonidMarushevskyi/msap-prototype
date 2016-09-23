'use strict';

angular.module('msapApp')
    .controller('LookupGenderDetailController', function ($scope, $rootScope, $stateParams, entity, LookupGender) {
        $scope.lookupGender = entity;
        $scope.load = function (id) {
            LookupGender.get({id: id}, function(result) {
                $scope.lookupGender = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupGenderUpdate', function(event, result) {
            $scope.lookupGender = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
