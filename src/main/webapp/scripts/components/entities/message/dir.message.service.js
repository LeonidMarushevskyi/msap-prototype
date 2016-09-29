'use strict';

angular.module('msapApp')
    .factory('MailService', function ($resource) {
        return $resource('api/mails/:dir/:search', {}, {
            'get': { method: 'GET', isArray: true }
        });
    });
