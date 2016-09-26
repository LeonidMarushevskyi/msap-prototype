'use strict';

angular.module('msapApp')
    .factory('LookupLicenseType', function ($resource) {
        return $resource('api/lookupLicenseTypes/:id', {}, {
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
