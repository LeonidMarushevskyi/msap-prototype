'use strict';

angular.module('msapApp')
    .factory('LookupSpecialNeedTypeSearch', function ($resource) {
        return $resource('api/_search/lookupSpecialNeedTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
