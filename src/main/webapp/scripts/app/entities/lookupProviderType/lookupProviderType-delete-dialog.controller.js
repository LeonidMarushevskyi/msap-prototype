'use strict';

angular.module('msapApp')
	.controller('LookupProviderTypeDeleteController', function($scope, $uibModalInstance, entity, LookupProviderType) {

        $scope.lookupProviderType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupProviderType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
