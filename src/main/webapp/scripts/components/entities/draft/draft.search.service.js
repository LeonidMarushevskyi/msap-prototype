'use strict';

angular.module('msapApp')
    .factory('DraftSearch', function ($resource) {
        return $resource('api/_search/drafts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
