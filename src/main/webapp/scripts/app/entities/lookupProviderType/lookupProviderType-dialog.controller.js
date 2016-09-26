'use strict';

angular.module('msapApp').controller('LookupProviderTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupProviderType',
        function($scope, $stateParams, $uibModalInstance, entity, LookupProviderType) {

        $scope.lookupProviderType = entity;
        $scope.load = function(id) {
            LookupProviderType.get({id : id}, function(result) {
                $scope.lookupProviderType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupProviderTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupProviderType.id != null) {
                LookupProviderType.update($scope.lookupProviderType, onSaveSuccess, onSaveError);
            } else {
                LookupProviderType.save($scope.lookupProviderType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
