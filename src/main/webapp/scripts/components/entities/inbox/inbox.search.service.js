'use strict';

angular.module('msapApp')
    .factory('InboxSearch', function ($resource) {
        return $resource('api/_search/inboxs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
