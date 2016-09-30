'use strict';

angular.module('msapApp').controller('SubstantiatedAllegationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubstantiatedAllegation', 'Provider',
        function($scope, $stateParams, $uibModalInstance, entity, SubstantiatedAllegation, Provider) {

        $scope.substantiatedAllegation = entity;
        $scope.providers = Provider.query();
        $scope.load = function(id) {
            SubstantiatedAllegation.get({id : id}, function(result) {
                $scope.substantiatedAllegation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:substantiatedAllegationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.substantiatedAllegation.id != null) {
                SubstantiatedAllegation.update($scope.substantiatedAllegation, onSaveSuccess, onSaveError);
            } else {
                SubstantiatedAllegation.save($scope.substantiatedAllegation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForAllegationDate = {};

        $scope.datePickerForAllegationDate.status = {
            opened: false
        };

        $scope.datePickerForAllegationDateOpen = function() {
            $scope.datePickerForAllegationDate.status.opened = true;
        };
}]);
