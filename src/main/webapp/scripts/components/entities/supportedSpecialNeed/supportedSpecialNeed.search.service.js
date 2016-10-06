'use strict';

angular.module('msapApp')
    .factory('SupportedSpecialNeedSearch', function ($resource) {
        return $resource('api/_search/supportedSpecialNeeds/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
