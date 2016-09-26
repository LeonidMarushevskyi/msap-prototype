'use strict';

angular.module('msapApp').controller('LookupQualityRatingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupQualityRating',
        function($scope, $stateParams, $uibModalInstance, entity, LookupQualityRating) {

        $scope.lookupQualityRating = entity;
        $scope.load = function(id) {
            LookupQualityRating.get({id : id}, function(result) {
                $scope.lookupQualityRating = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupQualityRatingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupQualityRating.id != null) {
                LookupQualityRating.update($scope.lookupQualityRating, onSaveSuccess, onSaveError);
            } else {
                LookupQualityRating.save($scope.lookupQualityRating, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
