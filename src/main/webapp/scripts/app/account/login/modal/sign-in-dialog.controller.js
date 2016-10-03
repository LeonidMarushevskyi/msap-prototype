'use strict';

angular.module('msapApp')
    .controller('SignInModalCtrl',
        ['$scope', '$log', '$uibModalInstance',
        function ($scope, $log, $uibModalInstance) {
            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]
    );
