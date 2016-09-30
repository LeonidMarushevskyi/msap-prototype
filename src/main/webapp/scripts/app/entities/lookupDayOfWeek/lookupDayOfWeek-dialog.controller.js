'use strict';

angular.module('msapApp').controller('LookupDayOfWeekDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupDayOfWeek',
        function($scope, $stateParams, $uibModalInstance, entity, LookupDayOfWeek) {

        $scope.lookupDayOfWeek = entity;
        $scope.load = function(id) {
            LookupDayOfWeek.get({id : id}, function(result) {
                $scope.lookupDayOfWeek = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupDayOfWeekUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupDayOfWeek.id != null) {
                LookupDayOfWeek.update($scope.lookupDayOfWeek, onSaveSuccess, onSaveError);
            } else {
                LookupDayOfWeek.save($scope.lookupDayOfWeek, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
