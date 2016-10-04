'use strict';

angular.module('msapApp')
    .factory('LookupSpecialNeedGroupSearch', function ($resource) {
        return $resource('api/_search/lookupSpecialNeedGroups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
