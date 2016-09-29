'use strict';

angular.module('msapApp')
    .factory('ConfirmMessage', function ($resource) {
        return $resource('api/mails/confirm', {}, {
            'confirm': { method: 'POST' }
        });
    });
