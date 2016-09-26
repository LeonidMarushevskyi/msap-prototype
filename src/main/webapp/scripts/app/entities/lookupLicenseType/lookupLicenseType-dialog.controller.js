'use strict';

angular.module('msapApp').controller('LookupLicenseTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupLicenseType',
        function($scope, $stateParams, $uibModalInstance, entity, LookupLicenseType) {

        $scope.lookupLicenseType = entity;
        $scope.load = function(id) {
            LookupLicenseType.get({id : id}, function(result) {
                $scope.lookupLicenseType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupLicenseTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupLicenseType.id != null) {
                LookupLicenseType.update($scope.lookupLicenseType, onSaveSuccess, onSaveError);
            } else {
                LookupLicenseType.save($scope.lookupLicenseType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
