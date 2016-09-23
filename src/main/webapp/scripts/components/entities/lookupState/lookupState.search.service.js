'use strict';

angular.module('msapApp')
    .factory('LookupStateSearch', function ($resource) {
        return $resource('api/_search/lookupStates/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
