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
    .constant('ProviderType', {
        CENTER: {id: '01pt', name: 'Center', label: 'center'},
        GROUP_HOME: {id: '02pt', name: 'Group Home', label: 'group name'},
        NON_RELATIVE_IN_HOME: {id: '03pt', name: 'Non-Relative In-Home', label: 'non-relative in-home'},
        NON_RELATIVE_OUT_OF_HOME: {id: '04pt', name: 'Non-Relative Out-of-Home', label: 'non-relative out-of-home'},
        RELATIVE_IN_HOME: {id: '05pt', name: 'Relative In-Home', label: 'relative in-home'},
        RELATIVE_OUT_OF_HOME: {id: '06pt', name: 'Relative Out-of-Home', label: 'relative out-of-home'},
        SLOT_CONTRACTOR: {id: '07pt', name: 'Slot Contractor', label: 'slot contractor'}
    })
    .constant('QualityRating', {
        LOW: {id: '01qr', name: 'Low', color: 'red'},
        AVERAGE: {id: '02qr', name: 'Average', color: 'orange'},
        GOOD: {id: '03qr', name: 'Good', color: 'yellow'},
        VERY_GOOD: {id: '04qr', name: 'Very Good', color: 'blue'},
        EXCELLENT: {id: '05qr', name: 'Excellent', color: 'green'},
        NOT_RATED: {id: '06qr', name: 'Not Rated', color: 'gray'}
    })
    .constant('chCustomScrollConfig', {
        autoHideScrollbar: false,
        theme: 'inset-2-dark',
        advanced: {
            updateOnContentResize: true
        },
        scrollInertia: 0
    });
