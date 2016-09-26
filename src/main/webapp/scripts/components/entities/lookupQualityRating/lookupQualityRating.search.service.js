'use strict';

angular.module('msapApp')
    .factory('LookupQualityRatingSearch', function ($resource) {
        return $resource('api/_search/lookupQualityRatings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
