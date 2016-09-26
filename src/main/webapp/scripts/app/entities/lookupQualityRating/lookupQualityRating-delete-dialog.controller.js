'use strict';

angular.module('msapApp')
	.controller('LookupQualityRatingDeleteController', function($scope, $uibModalInstance, entity, LookupQualityRating) {

        $scope.lookupQualityRating = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupQualityRating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
