'use strict';

angular.module('msapApp')
    .factory('SupportedLanguageSearch', function ($resource) {
        return $resource('api/_search/supportedLanguages/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
