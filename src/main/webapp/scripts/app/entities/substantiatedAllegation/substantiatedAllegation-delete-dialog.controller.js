'use strict';

angular.module('msapApp')
	.controller('SubstantiatedAllegationDeleteController', function($scope, $uibModalInstance, entity, SubstantiatedAllegation) {

        $scope.substantiatedAllegation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SubstantiatedAllegation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
