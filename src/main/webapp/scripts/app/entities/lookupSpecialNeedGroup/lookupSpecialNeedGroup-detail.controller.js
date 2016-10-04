'use strict';

angular.module('msapApp')
    .controller('LookupSpecialNeedGroupDetailController', function ($scope, $rootScope, $stateParams, entity, LookupSpecialNeedGroup) {
        $scope.lookupSpecialNeedGroup = entity;
        $scope.load = function (id) {
            LookupSpecialNeedGroup.get({id: id}, function(result) {
                $scope.lookupSpecialNeedGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupSpecialNeedGroupUpdate', function(event, result) {
            $scope.lookupSpecialNeedGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
