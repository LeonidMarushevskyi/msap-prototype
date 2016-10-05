'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ch-facilities', {
                parent: 'site',
                url: '/facilities',
                data: {
                    //authorities: ['PARENT', 'FOSTER_PARENT', ],
                    pageTitle: ''
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/facilities/facilities.html',
                        controller: 'FacilitiesController'
                    },
                    'aside@ch-facilities': {
                        templateUrl: 'scripts/app/facilities/facilities-agencies.html',
                        controller: 'FacilitiesAgenciesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('facilities');
                        $translatePartialLoader.addPart('lookups');
                        return $translate.refresh();
                    }],
                    searchParams: ['$state', '$q', function($state, $q) {
                        return $q.when($state.params);
                    }],
                    lookupAgeGroups: ['LookupAgeGroups', function(LookupAgeGroups) {
                        return LookupAgeGroups.query().$promise;
                    }],
                    lookupQualityRating: ['LookupQualityRating', function(LookupQualityRating) {
                        return LookupQualityRating.query().$promise;
                    }],
                    lookupProviderType: ['LookupProviderType', function(LookupProviderType) {
                        return LookupProviderType.query().$promise;
                    }],
                    lookupWorkingHours: ['$q', 'lookupWorkingHours', function ($q, lookupWorkingHours) {
                        return $q.when(lookupWorkingHours); // lookupWorkingHours is defined in constants.js
                    }],
                    lookupSpecialNeedGroup: ['LookupSpecialNeedGroup', function(LookupSpecialNeedGroup) {
                        return LookupSpecialNeedGroup.query().$promise;
                    }],
                    lookupSpecialNeedType: ['LookupSpecialNeedType', function(LookupSpecialNeedType) {
                        return LookupSpecialNeedType.query().$promise;
                    }],
                    lookupLicenseType: ['LookupLicenseType', function(LookupLicenseType) {
                        return LookupLicenseType.query().$promise;
                    }]
                }
            });
    });
