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
    ])
    .constant('lookupWeeklyPriceRanges', [
        {id: 1, code: 1, name: '0-25', min: 0, max: 25 },          // [ 0, 25 )
        {id: 2, code: 2, name: '25-50', min: 25, max: 50 },        // [ 25, 50 )
        {id: 3, code: 3, name: '50-75', min: 50, max: 75 },        // [ 50, 75 )
        {id: 4, code: 4, name: '75-100', min: 75, max: 100 },      // [ 75, 100 )
        {id: 5, code: 5, name: '100-125', min: 100, max: 125 },    // [ 100, 125 )
        {id: 6, code: 6, name: '125-150', min: 125, max: 150 },    // [ 125, 150 )
        {id: 7, code: 7, name: '150+', min: 150, max: null }       // [ 150, ∞ )
    ]);
