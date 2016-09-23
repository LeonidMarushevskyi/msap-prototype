'use strict';

angular.module('msapApp')
	.controller('LookupMaritalStatusDeleteController', function($scope, $uibModalInstance, entity, LookupMaritalStatus) {

        $scope.lookupMaritalStatus = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupMaritalStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
