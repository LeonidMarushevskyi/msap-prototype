'use strict';

angular.module('msapApp').controller('SupportedSpecialNeedDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SupportedSpecialNeed', 'Provider', 'LookupSpecialNeedType',
        function($scope, $stateParams, $uibModalInstance, $q, entity, SupportedSpecialNeed, Provider, LookupSpecialNeedType) {

        $scope.supportedSpecialNeed = entity;
        $scope.providers = Provider.query();
        $scope.specialneedtypes = LookupSpecialNeedType.query({filter: 'supportedspecialneed-is-null'});
        $q.all([$scope.supportedSpecialNeed.$promise, $scope.specialneedtypes.$promise]).then(function() {
            if (!$scope.supportedSpecialNeed.specialNeedType || !$scope.supportedSpecialNeed.specialNeedType.id) {
                return $q.reject();
            }
            return LookupSpecialNeedType.get({id : $scope.supportedSpecialNeed.specialNeedType.id}).$promise;
        }).then(function(specialNeedType) {
            $scope.specialneedtypes.push(specialNeedType);
        });
        $scope.load = function(id) {
            SupportedSpecialNeed.get({id : id}, function(result) {
                $scope.supportedSpecialNeed = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:supportedSpecialNeedUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supportedSpecialNeed.id != null) {
                SupportedSpecialNeed.update($scope.supportedSpecialNeed, onSaveSuccess, onSaveError);
            } else {
                SupportedSpecialNeed.save($scope.supportedSpecialNeed, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
