'use strict';

angular.module('msapApp')
    .factory('SupportedLanguage', function ($resource) {
        return $resource('api/supportedLanguages/:id', {}, {
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
