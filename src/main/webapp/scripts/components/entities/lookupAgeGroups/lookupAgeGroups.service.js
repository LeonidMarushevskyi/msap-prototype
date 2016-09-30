'use strict';

angular.module('msapApp')
    .factory('LookupAgeGroups', function ($resource) {
        return $resource('api/lookupAgeGroupss/:id', {}, {
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
