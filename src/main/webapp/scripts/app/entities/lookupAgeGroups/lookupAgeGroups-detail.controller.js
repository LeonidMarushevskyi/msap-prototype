'use strict';

angular.module('msapApp')
    .controller('LookupAgeGroupsDetailController', function ($scope, $rootScope, $stateParams, entity, LookupAgeGroups) {
        $scope.lookupAgeGroups = entity;
        $scope.load = function (id) {
            LookupAgeGroups.get({id: id}, function(result) {
                $scope.lookupAgeGroups = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupAgeGroupsUpdate', function(event, result) {
            $scope.lookupAgeGroups = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
