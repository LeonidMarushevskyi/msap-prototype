'use strict';

angular.module('msapApp')
    .factory('Provider', function ($resource, DateUtils) {
        return $resource('api/providers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastVisit = DateUtils.convertLocaleDateFromServer(data.lastVisit);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastVisit = DateUtils.convertLocaleDateToServer(data.lastVisit);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastVisit = DateUtils.convertLocaleDateToServer(data.lastVisit);
                    return angular.toJson(data);
                }
            }
        });
    });
