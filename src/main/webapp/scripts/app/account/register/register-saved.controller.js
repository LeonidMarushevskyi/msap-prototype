'use strict';

angular.module('msapApp')
    .controller('RegisterSavedController', function ($scope, $state, $interval) {
        $scope.timeLeft = 20;

        $scope.interval = $interval(function() {
            $scope.timeLeft--;
            if ($scope.timeLeft === 0) {
                $interval.cancel($scope.interval);
                $state.go("home");
            }
        }, 1000);
    });
