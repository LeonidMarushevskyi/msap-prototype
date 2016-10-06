'use strict';

angular.module('msapApp')
    .factory('OpenSlot', function ($resource) {
        return $resource('api/openSlots/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' },
            'findAllByProvider': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('OpenSlotByProviderId', function ($resource) {
        return $resource('api/openSlots/byProvider/:providerId', {}, {
            'findAllByProviderId': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('SumOpenSlotsByProviderId', function ($resource) {
        return $resource('api/openSlots/sumOpenSlots/byProvider/{providerId}', {}, {
            'sumOpenSlotsByProviderId': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
