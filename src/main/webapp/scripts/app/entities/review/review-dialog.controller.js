'use strict';

angular.module('msapApp').controller('ReviewDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Review', 'Provider', 'User',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Review, Provider, User) {

        $scope.review = entity;
        $scope.providers = Provider.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Review.get({id : id}, function(result) {
                $scope.review = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:reviewUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.review.id != null) {
                Review.update($scope.review, onSaveSuccess, onSaveError);
            } else {
                Review.save($scope.review, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForReviewDate = {};

        $scope.datePickerForReviewDate.status = {
            opened: false
        };

        $scope.datePickerForReviewDateOpen = function() {
            $scope.datePickerForReviewDate.status.opened = true;
        };
}]);
