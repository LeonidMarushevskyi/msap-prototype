'use strict';

angular.module('msapApp')
    .factory('LookupDayOfWeek', function ($resource) {
        return $resource('api/lookupDayOfWeeks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
