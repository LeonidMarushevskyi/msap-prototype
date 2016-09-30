'use strict';

angular.module('msapApp')
    .factory('LookupLanguage', function ($resource) {
        return $resource('api/lookupLanguages/:id', {}, {
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
