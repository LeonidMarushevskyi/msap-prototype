'use strict';

angular.module('msapApp')
    .factory('LookupAgeGroupsSearch', function ($resource) {
        return $resource('api/_search/lookupAgeGroupss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
