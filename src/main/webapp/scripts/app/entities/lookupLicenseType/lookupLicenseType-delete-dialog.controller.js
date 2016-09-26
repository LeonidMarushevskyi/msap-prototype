'use strict';

angular.module('msapApp')
	.controller('LookupLicenseTypeDeleteController', function($scope, $uibModalInstance, entity, LookupLicenseType) {

        $scope.lookupLicenseType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupLicenseType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
