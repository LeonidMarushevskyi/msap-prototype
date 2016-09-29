'use strict';

angular.module('msapApp')
    .factory('MailBoxSearch', function ($resource) {
        return $resource('api/_search/mailBoxs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
