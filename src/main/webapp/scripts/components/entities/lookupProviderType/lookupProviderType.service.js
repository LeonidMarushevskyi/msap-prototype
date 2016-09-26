'use strict';

angular.module('msapApp')
    .factory('LookupProviderType', function ($resource) {
        return $resource('api/lookupProviderTypes/:id', {}, {
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
