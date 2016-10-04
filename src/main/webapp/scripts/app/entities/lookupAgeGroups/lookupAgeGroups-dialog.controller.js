'use strict';

angular.module('msapApp').controller('LookupAgeGroupsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupAgeGroups',
        function($scope, $stateParams, $uibModalInstance, entity, LookupAgeGroups) {

        $scope.lookupAgeGroups = entity;
        $scope.load = function(id) {
            LookupAgeGroups.get({id : id}, function(result) {
                $scope.lookupAgeGroups = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupAgeGroupsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupAgeGroups.id != null) {
                LookupAgeGroups.update($scope.lookupAgeGroups, onSaveSuccess, onSaveError);
            } else {
                LookupAgeGroups.save($scope.lookupAgeGroups, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
