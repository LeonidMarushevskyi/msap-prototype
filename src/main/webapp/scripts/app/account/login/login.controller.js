'use strict';

angular.module('msapApp')
    .controller('LoginController', function ($scope, $uibModal) {
        $scope.openSignInModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/login/modal/sign-in-dialog.html',
                controller: 'SignInModalCtrl',
                size: 'sign-in',
                windowClass: 'ch-general-modal'
            });
        };
    });
