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

        $scope.initTwitterTimeline = function () {
            $("#twitter-wjs").remove();

            !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
        };

        $scope.initTwitterTimeline();
    });
