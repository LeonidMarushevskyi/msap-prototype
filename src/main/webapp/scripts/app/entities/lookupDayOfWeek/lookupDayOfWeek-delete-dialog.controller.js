'use strict';

angular.module('msapApp')
	.controller('LookupDayOfWeekDeleteController', function($scope, $uibModalInstance, entity, LookupDayOfWeek) {

        $scope.lookupDayOfWeek = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LookupDayOfWeek.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
