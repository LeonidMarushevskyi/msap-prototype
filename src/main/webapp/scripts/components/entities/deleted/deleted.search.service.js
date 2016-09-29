'use strict';

angular.module('msapApp')
    .factory('DeletedSearch', function ($resource) {
        return $resource('api/_search/deleteds/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
