'use strict';

angular.module('msapApp')
	.controller('SupportedSpecialNeedDeleteController', function($scope, $uibModalInstance, entity, SupportedSpecialNeed) {

        $scope.supportedSpecialNeed = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupportedSpecialNeed.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
