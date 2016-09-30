'use strict';

angular.module('msapApp')
	.controller('LookupSpecialNeedGroupDeleteController', function($scope, $uibModalInstance, entity, LookupSpecialNeedGroup) {

        $scope.lookupSpecialNeedGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupSpecialNeedGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
