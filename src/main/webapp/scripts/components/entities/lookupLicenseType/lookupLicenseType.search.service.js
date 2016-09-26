'use strict';

angular.module('msapApp')
    .factory('LookupLicenseTypeSearch', function ($resource) {
        return $resource('api/_search/lookupLicenseTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
