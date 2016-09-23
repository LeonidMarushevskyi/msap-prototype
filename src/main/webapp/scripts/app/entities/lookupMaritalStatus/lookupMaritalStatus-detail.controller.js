'use strict';

angular.module('msapApp')
    .controller('LookupMaritalStatusDetailController', function ($scope, $rootScope, $stateParams, entity, LookupMaritalStatus) {
        $scope.lookupMaritalStatus = entity;
        $scope.load = function (id) {
            LookupMaritalStatus.get({id: id}, function(result) {
                $scope.lookupMaritalStatus = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupMaritalStatusUpdate', function(event, result) {
            $scope.lookupMaritalStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
