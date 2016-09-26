'use strict';

angular.module('msapApp')
	.controller('ProviderDeleteController', function($scope, $uibModalInstance, entity, Provider) {

        $scope.provider = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Provider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
