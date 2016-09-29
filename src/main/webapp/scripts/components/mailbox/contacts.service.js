'use strict';

angular.module('msapApp')
    .factory('Contacts', function ($resource) {
        return $resource('api/contacts', {}, {
                'all': {method: 'GET', isArray: true},
                'avl': {method: 'POST', isArray: true}
            });
        });
