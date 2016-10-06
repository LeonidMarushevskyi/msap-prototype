'use strict';

angular.module('msapApp')
    .factory('SupportedSpecialNeed', function ($resource) {
        return $resource('api/supportedSpecialNeeds/:id', {}, {
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
