'use strict';

angular.module('msapApp')
    .constant('SecurityRole', {
        ADMIN: 'ROLE_ADMIN',
        CASE_WORKER: 'CASE_WORKER',
        PARENT: 'PARENT'
    })
    .constant('uibCustomDatepickerConfig', {
        formatYear: 'yyyy',
        startingDay: 1,
        showWeeks: false
    })
    .constant('QualityRatingStars', {
        NOT_RATED: {code: 0, stars: '1star'},
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
    });
