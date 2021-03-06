'use strict';

angular.module('msapApp')
    .controller('AccessDeniedController',
        ['$scope', '$state', 'Principal',
            function ($scope, $state, Principal) {

                Principal.identity(true).then(function(account) {

                    if (!_.isObject(account)) {
                        $state.go("home");
                    }

                });
                return this;
            }
        ]);
