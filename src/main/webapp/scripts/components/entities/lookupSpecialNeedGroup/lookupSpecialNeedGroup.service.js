'use strict';

angular.module('msapApp')
    .factory('LookupSpecialNeedGroup', function ($resource) {
        return $resource('api/lookupSpecialNeedGroups/:id', {}, {
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
