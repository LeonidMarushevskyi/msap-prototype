'use strict';

angular.module('msapApp').controller('ScheduleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Schedule', 'Provider', 'LookupDayOfWeek',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Schedule, Provider, LookupDayOfWeek) {

        $scope.schedule = entity;
        $scope.providers = Provider.query();
        $scope.dayofweeks = LookupDayOfWeek.query({filter: 'schedule-is-null'});
        $q.all([$scope.schedule.$promise, $scope.dayofweeks.$promise]).then(function() {
            if (!$scope.schedule.dayOfWeek || !$scope.schedule.dayOfWeek.id) {
                return $q.reject();
            }
            return LookupDayOfWeek.get({id : $scope.schedule.dayOfWeek.id}).$promise;
        }).then(function(dayOfWeek) {
            $scope.dayofweeks.push(dayOfWeek);
        });
        $scope.load = function(id) {
            Schedule.get({id : id}, function(result) {
                $scope.schedule = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:scheduleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.schedule.id != null) {
                Schedule.update($scope.schedule, onSaveSuccess, onSaveError);
            } else {
                Schedule.save($scope.schedule, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
