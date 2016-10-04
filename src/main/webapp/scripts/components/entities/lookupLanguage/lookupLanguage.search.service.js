'use strict';

angular.module('msapApp')
    .factory('LookupLanguageSearch', function ($resource) {
        return $resource('api/_search/lookupLanguages/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
