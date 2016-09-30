'use strict';

angular.module('msapApp').controller('LookupSpecialNeedTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LookupSpecialNeedType', 'LookupSpecialNeedGroup', 'Provider',
        function($scope, $stateParams, $uibModalInstance, $q, entity, LookupSpecialNeedType, LookupSpecialNeedGroup, Provider) {

        $scope.lookupSpecialNeedType = entity;
        $scope.specialneedgroups = LookupSpecialNeedGroup.query({filter: 'lookupspecialneedtype-is-null'});
        $q.all([$scope.lookupSpecialNeedType.$promise, $scope.specialneedgroups.$promise]).then(function() {
            if (!$scope.lookupSpecialNeedType.specialNeedGroup || !$scope.lookupSpecialNeedType.specialNeedGroup.id) {
                return $q.reject();
            }
            return LookupSpecialNeedGroup.get({id : $scope.lookupSpecialNeedType.specialNeedGroup.id}).$promise;
        }).then(function(specialNeedGroup) {
            $scope.specialneedgroups.push(specialNeedGroup);
        });
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
