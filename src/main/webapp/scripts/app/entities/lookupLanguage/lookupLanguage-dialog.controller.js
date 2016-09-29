'use strict';

angular.module('msapApp').controller('LookupLanguageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LookupLanguage', 'Provider',
        function($scope, $stateParams, $uibModalInstance, entity, LookupLanguage, Provider) {

        $scope.lookupLanguage = entity;
        $scope.providers = Provider.query();
        $scope.load = function(id) {
            LookupLanguage.get({id : id}, function(result) {
                $scope.lookupLanguage = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:lookupLanguageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lookupLanguage.id != null) {
                LookupLanguage.update($scope.lookupLanguage, onSaveSuccess, onSaveError);
            } else {
                LookupLanguage.save($scope.lookupLanguage, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
