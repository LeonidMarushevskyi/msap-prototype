'use strict';

angular.module('msapApp')
    .factory('LookupDayOfWeekSearch', function ($resource) {
        return $resource('api/_search/lookupDayOfWeeks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
