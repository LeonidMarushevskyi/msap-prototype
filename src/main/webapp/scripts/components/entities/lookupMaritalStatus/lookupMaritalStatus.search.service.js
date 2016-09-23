'use strict';

angular.module('msapApp')
    .factory('LookupMaritalStatusSearch', function ($resource) {
        return $resource('api/_search/lookupMaritalStatuss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
