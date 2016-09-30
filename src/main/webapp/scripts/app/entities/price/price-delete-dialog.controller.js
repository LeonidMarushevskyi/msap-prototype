'use strict';

angular.module('msapApp')
	.controller('PriceDeleteController', function($scope, $uibModalInstance, entity, Price) {

        $scope.price = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Price.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
