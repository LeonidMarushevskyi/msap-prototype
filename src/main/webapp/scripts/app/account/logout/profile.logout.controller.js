'use strict';

angular.module('msapApp')
    .controller('MyProfileAndLogoutController', ['$scope', '$state', '$uibModal', 'Auth', 'Principal', 'MailBoxService', 'StorageService', 'sessionAddress',
            function ($scope, $state, $uibModal, Auth, Principal, MailBoxService, StorageService, sessionAddress) {
        $scope.isAuthenticated = Principal.isAuthenticated;

        $scope.logout = function () {
            MailBoxService.disconnect();
            Auth.logout();
            $scope.isAccountPopupVisible = false;
            StorageService.removeSession(sessionAddress.SESSION_ADDRESS);
            $state.go('home', {}, {reload: true});
        };

        $scope.hasAnyRole = function() {
            return $scope.account && $scope.account.authorities;
        };

        $scope.openSignInModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/login/modal/sign-in-dialog.html',
                controller: 'SignInModalCtrl',
                size: 'sign-in',
                windowClass: 'ch-general-modal'
            });
        };

        $scope.openRegisterModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/register/register.html',
                controller: 'RegisterController',
                size: 'register',
                windowClass: 'ch-general-modal'
            });
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
            if (!_.isNil(account)) {
                 $scope.accountName = ((_.isNil(account.firstName) ? '' : account.firstName)
                    + (_.isNil(account.lastName) ? '' : ' ' + account.lastName)).trim();
            }
        });

    }]);
