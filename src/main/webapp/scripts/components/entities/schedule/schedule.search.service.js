'use strict';

angular.module('msapApp')
    .factory('ScheduleSearch', function ($resource) {
        return $resource('api/_search/schedules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
