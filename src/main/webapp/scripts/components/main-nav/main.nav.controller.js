'use strict';

angular.module('msapApp')
    .controller('MainNavController', function ($scope, $rootScope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        Principal.identity().then(function(account) {
            $scope.account = account;
        });

        $scope.$on("msapApp:updateUnreadInboxCount", function(event, unreadInboxCount) {
            $scope.unreadInboxCount = unreadInboxCount;
        });

        $scope.inkRipple = function() {
            var parent, ink, d, x, y;
            $(".ch-ink-btn").click(function(e){

                parent = $(this).parent();
                if(parent.find(".ink").length === 0)
                    $(".ch-ink-btn").append("<span class='ink'></span>");

                ink = parent.find(".ink");
                ink.removeClass("animate");

                //set size of .ink
                if(!ink.height() && !ink.width()) {
                    d = Math.max(parent.outerWidth(), parent.outerHeight());
                    ink.css({height: d, width: d});
                }

                x = e.pageX - parent.offset().left - ink.width()/2;
                y = e.pageY - parent.offset().top - ink.height()/2;

                ink.css({top: y+'px', left: x+'px'}).addClass("animate");
            })
        }
    });
