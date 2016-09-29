'use strict';

angular.module('msapApp')
    .factory('FileService', function ($resource) {
        return $resource('api/file/:id', {}, {

        });
    });
