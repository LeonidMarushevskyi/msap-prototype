'use strict';

angular.module('msapApp')
    .factory('ProviderSearch', function ($resource) {
        return $resource('api/_search/providers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
