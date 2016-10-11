'use strict';

angular.module('msapApp')
    .constant('sessionAddress', {
        SESSION_ADDRESS: 'session_address'
    })
    .constant('SecurityRole', {
        ADMIN: 'ROLE_ADMIN',
        CASE_WORKER: 'CASE_WORKER',
        PARENT: 'PARENT',
        FOSTER_PARENT: 'FOSTER_PARENT'
    })
    .constant('uibCustomDatepickerConfig', {
        formatYear: 'yyyy',
        startingDay: 1,
        showWeeks: false
    })
    .constant('QualityRatingStars', {
        NOT_RATED: {code: 0, stars: 'not_rated'},
        LOW: {code: 1, stars: '1star'},
        AVERAGE: {code: 2, stars: '2star'},
        GOOD: {code: 3, stars: '3star'},
        VERY_GOOD: {code: 4, stars: '4star'},
        EXCELLENT: {code: 5, stars: '5star'}
    })
    .constant('chCustomScrollConfig', {
        autoHideScrollbar: false,
        theme: 'inset-2-dark',
        advanced: {
            updateOnContentResize: true
        },
        scrollInertia: 0
    })
    .constant('lookupWorkingHours', [
        {code: 1, name: 'Before School Care'},
        {code: 2, name: 'After School Care'},
        {code: 3, name: 'Full Day Care'},
        {code: 4, name: 'Weekend Care'},
        {code: 5, name: 'Overnight Care'}
    ]);
