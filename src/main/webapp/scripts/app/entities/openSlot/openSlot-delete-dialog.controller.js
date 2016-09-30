'use strict';

angular.module('msapApp')
	.controller('OpenSlotDeleteController', function($scope, $uibModalInstance, entity, OpenSlot) {

        $scope.openSlot = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OpenSlot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
