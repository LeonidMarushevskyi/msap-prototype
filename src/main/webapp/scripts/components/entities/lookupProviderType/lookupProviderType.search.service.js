'use strict';

angular.module('msapApp')
    .factory('LookupProviderTypeSearch', function ($resource) {
        return $resource('api/_search/lookupProviderTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
