'use strict';

angular.module('msapApp')
	.controller('DeletedDeleteController', function($scope, $uibModalInstance, entity, Deleted) {

        $scope.deleted = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Deleted.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
