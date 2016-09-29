'use strict';

angular.module('msapApp')
    .factory('SubstantiatedAllegationSearch', function ($resource) {
        return $resource('api/_search/substantiatedAllegations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
