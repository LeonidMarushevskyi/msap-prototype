'use strict';

angular.module('msapApp')
    .factory('PriceSearch', function ($resource) {
        return $resource('api/_search/prices/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
