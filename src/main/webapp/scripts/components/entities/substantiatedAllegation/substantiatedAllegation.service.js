'use strict';

angular.module('msapApp')
    .factory('SubstantiatedAllegation', function ($resource, DateUtils) {
        return $resource('api/substantiatedAllegations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.allegationDate = DateUtils.convertLocaleDateFromServer(data.allegationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.allegationDate = DateUtils.convertLocaleDateToServer(data.allegationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.allegationDate = DateUtils.convertLocaleDateToServer(data.allegationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
