'use strict';

angular.module('msapApp')
	.controller('SupportedLanguageDeleteController', function($scope, $uibModalInstance, entity, SupportedLanguage) {

        $scope.supportedLanguage = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SupportedLanguage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
