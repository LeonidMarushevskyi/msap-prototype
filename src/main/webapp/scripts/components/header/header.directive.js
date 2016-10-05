angular.module('msapApp')
    .directive('showUserAccountPopup', function($timeout, $document) {
        return {
            restrict: 'A',
            link: function(scope, element) {
                var timer;
                scope.isUserAccountPopupShown = false;
                scope.toggleUserPopup = function() {
                    scope.isUserAccountPopupShown = !scope.isUserAccountPopupShown;
                };
                scope.autoHideUserPopup = function() {
                    timer = $timeout(function () {
                        scope.isUserAccountPopupShown = false;
                    }, 2000);
                };

                scope.clearAutoHideUserPopup = function() {
                    if (timer) {
                        $timeout.cancel(timer);
                    }
                };

                var slideout = new Slideout({
                  'panel': document.getElementById('panel'),
                  'menu': document.getElementById('menu'),
                  'padding': 256,
                  'tolerance': 70
                });

                $document.on('click', function (e) {
                    if (element !== e.target && !element[0].contains(e.target)) {
                        scope.$apply(function () {
                            scope.isUserAccountPopupShown = false;
                        });
                    }
                });
            }
        };
    });
