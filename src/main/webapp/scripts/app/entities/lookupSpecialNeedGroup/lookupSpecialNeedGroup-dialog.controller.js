'use strict';

angular.module('msapApp').controller('LookupSpecialNeedGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupSpecialNeedGroup',
        function($scope, $stateParams, $uibModalInstance, entity, LookupSpecialNeedGroup) {

        $scope.lookupSpecialNeedGroup = entity;
        $scope.load = function(id) {
            LookupSpecialNeedGroup.get({id : id}, function(result) {
                $scope.lookupSpecialNeedGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupSpecialNeedGroupUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupSpecialNeedGroup.id != null) {
                LookupSpecialNeedGroup.update($scope.lookupSpecialNeedGroup, onSaveSuccess, onSaveError);
            } else {
                LookupSpecialNeedGroup.save($scope.lookupSpecialNeedGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
