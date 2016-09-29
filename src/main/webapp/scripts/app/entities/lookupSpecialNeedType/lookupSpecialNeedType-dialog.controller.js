'use strict';

angular.module('msapApp').controller('LookupSpecialNeedTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupSpecialNeedType', 'Provider',
        function($scope, $stateParams, $uibModalInstance, entity, LookupSpecialNeedType, Provider) {

        $scope.lookupSpecialNeedType = entity;
        $scope.providers = Provider.query();
        $scope.load = function(id) {
            LookupSpecialNeedType.get({id : id}, function(result) {
                $scope.lookupSpecialNeedType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupSpecialNeedTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupSpecialNeedType.id != null) {
                LookupSpecialNeedType.update($scope.lookupSpecialNeedType, onSaveSuccess, onSaveError);
            } else {
                LookupSpecialNeedType.save($scope.lookupSpecialNeedType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
