'use strict';

angular.module('msapApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


