'use strict';

angular.module('msapApp')
    .factory('LookupSpecialNeedType', function ($resource) {
        return $resource('api/lookupSpecialNeedTypes/:id', {}, {
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
