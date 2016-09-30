'use strict';

angular.module('msapApp')
    .factory('OpenSlotSearch', function ($resource) {
        return $resource('api/_search/openSlots/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
