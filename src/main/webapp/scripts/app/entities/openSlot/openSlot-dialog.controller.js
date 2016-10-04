'use strict';

angular.module('msapApp').controller('OpenSlotDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'OpenSlot', 'Provider', 'LookupAgeGroups',
        function($scope, $stateParams, $uibModalInstance, $q, entity, OpenSlot, Provider, LookupAgeGroups) {

        $scope.openSlot = entity;
        $scope.providers = Provider.query();
        $scope.agegroups = LookupAgeGroups.query({filter: 'openslot-is-null'});
        $q.all([$scope.openSlot.$promise, $scope.agegroups.$promise]).then(function() {
            if (!$scope.openSlot.ageGroup || !$scope.openSlot.ageGroup.id) {
                return $q.reject();
            }
            return LookupAgeGroups.get({id : $scope.openSlot.ageGroup.id}).$promise;
        }).then(function(ageGroup) {
            $scope.agegroups.push(ageGroup);
        });
        $scope.load = function(id) {
            OpenSlot.get({id : id}, function(result) {
                $scope.openSlot = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:openSlotUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.openSlot.id != null) {
                OpenSlot.update($scope.openSlot, onSaveSuccess, onSaveError);
            } else {
                OpenSlot.save($scope.openSlot, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
