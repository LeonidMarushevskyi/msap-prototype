'use strict';

angular.module('msapApp').controller('SupportedLanguageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SupportedLanguage', 'Provider', 'LookupLanguage',
        function($scope, $stateParams, $uibModalInstance, $q, entity, SupportedLanguage, Provider, LookupLanguage) {

        $scope.supportedLanguage = entity;
        $scope.providers = Provider.query();
        $scope.languages = LookupLanguage.query({filter: 'supportedlanguage-is-null'});
        $q.all([$scope.supportedLanguage.$promise, $scope.languages.$promise]).then(function() {
            if (!$scope.supportedLanguage.language || !$scope.supportedLanguage.language.id) {
                return $q.reject();
            }
            return LookupLanguage.get({id : $scope.supportedLanguage.language.id}).$promise;
        }).then(function(language) {
            $scope.languages.push(language);
        });
        $scope.load = function(id) {
            SupportedLanguage.get({id : id}, function(result) {
                $scope.supportedLanguage = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:supportedLanguageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.supportedLanguage.id != null) {
                SupportedLanguage.update($scope.supportedLanguage, onSaveSuccess, onSaveError);
            } else {
                SupportedLanguage.save($scope.supportedLanguage, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
