'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ch-facilities', {
                parent: 'site',
                url: '/facilities',
                data: {
                    authorities: ['PARENT'],
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
                        return $translate.refresh();
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
                    lookupWorkingHours: ['$q', function ($q) {
                        return $q.when([
                            {code: 1, name: 'Before School Care'},
                            {code: 2, name: 'After School Care'},
                            {code: 3, name: 'Full Day Care'},
                            {code: 4, name: 'Weekend Care'},
                            {code: 5, name: 'Overnight Care'}
                        ]);
                    }]
                }
            });
    });
