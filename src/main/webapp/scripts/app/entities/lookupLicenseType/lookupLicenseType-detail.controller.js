'use strict';

angular.module('msapApp')
    .controller('LookupLicenseTypeDetailController', function ($scope, $rootScope, $stateParams, entity, LookupLicenseType) {
        $scope.lookupLicenseType = entity;
        $scope.load = function (id) {
            LookupLicenseType.get({id: id}, function(result) {
                $scope.lookupLicenseType = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupLicenseTypeUpdate', function(event, result) {
            $scope.lookupLicenseType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
