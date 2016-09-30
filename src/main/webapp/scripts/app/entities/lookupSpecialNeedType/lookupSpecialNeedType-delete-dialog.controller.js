'use strict';

angular.module('msapApp')
	.controller('LookupSpecialNeedTypeDeleteController', function($scope, $uibModalInstance, entity, LookupSpecialNeedType) {

        $scope.lookupSpecialNeedType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupSpecialNeedType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
