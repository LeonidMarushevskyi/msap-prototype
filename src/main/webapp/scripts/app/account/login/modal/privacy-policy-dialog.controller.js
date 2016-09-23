'use strict';

angular.module('msapApp')
    .controller('PrivacyPolicyModalCtrl',
        ['$scope', '$log', '$uibModalInstance',
        function ($scope, $log, $uibModalInstance) {
            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]
    );
