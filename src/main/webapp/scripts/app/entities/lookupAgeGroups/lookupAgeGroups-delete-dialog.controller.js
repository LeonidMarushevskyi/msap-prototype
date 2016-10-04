'use strict';

angular.module('msapApp')
	.controller('LookupAgeGroupsDeleteController', function($scope, $uibModalInstance, entity, LookupAgeGroups) {

        $scope.lookupAgeGroups = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupAgeGroups.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
