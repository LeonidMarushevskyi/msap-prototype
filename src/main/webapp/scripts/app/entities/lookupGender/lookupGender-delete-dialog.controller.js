'use strict';

angular.module('msapApp')
	.controller('LookupGenderDeleteController', function($scope, $uibModalInstance, entity, LookupGender) {

        $scope.lookupGender = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupGender.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
