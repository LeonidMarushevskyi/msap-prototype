'use strict';

angular.module('msapApp').controller('LookupStateDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupState',
        function($scope, $stateParams, $uibModalInstance, entity, LookupState) {

        $scope.lookupState = entity;
        $scope.load = function(id) {
            LookupState.get({id : id}, function(result) {
                $scope.lookupState = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupStateUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupState.id != null) {
                LookupState.update($scope.lookupState, onSaveSuccess, onSaveError);
            } else {
                LookupState.save($scope.lookupState, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
