'use strict';

angular.module('msapApp')
	.controller('LookupLanguageDeleteController', function($scope, $uibModalInstance, entity, LookupLanguage) {

        $scope.lookupLanguage = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupLanguage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
