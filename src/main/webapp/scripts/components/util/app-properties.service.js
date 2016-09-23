'use strict';

angular.module('msapApp')
    .factory('AppPropertiesService', function ($resource) {
        return {
            defaultAddress: $resource('api/app-properties/default-address', {}, {
                'defaultAddress': {
                    method: 'GET',
                    //because of http://stackoverflow.com/questions/24876593/resource-query-return-split-strings-array-of-char-instead-of-a-string
                    transformResponse: function (data) {
                        return {data: data};
                    }
                }
            }).defaultAddress
        };
    });
