'use strict';

angular.module('msapApp')
    .factory('LookupCountySearch', function ($resource) {
        return $resource('api/_search/lookupCountys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
