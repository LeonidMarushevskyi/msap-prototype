'use strict';

angular.module('msapApp')
    .factory('MessageThread', function ($resource) {
        return $resource('api/mails/thread/:id', {}, {
            'get': { method: 'GET' }
        });
    });
