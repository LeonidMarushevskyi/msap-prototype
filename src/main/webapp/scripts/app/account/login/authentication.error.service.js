'use strict';

angular.module('msapApp')
    .service('AuthenticationErrorService', function () {

        var authenticationError = null;

        return {
            setAuthenticationError: function(error) {
                authenticationError = error;
            },

            resetAuthenticationError: function() {
                authenticationError = null;
            },

            getAuthenticationError: function() {
                return authenticationError;
            }
        }
    });
