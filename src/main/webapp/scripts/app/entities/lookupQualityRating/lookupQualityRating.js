'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupQualityRating', {
                parent: 'entity',
                url: '/lookupQualityRatings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupQualityRating.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupQualityRating/lookupQualityRatings.html',
                        controller: 'LookupQualityRatingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupQualityRating');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupQualityRating.detail', {
                parent: 'entity',
                url: '/lookupQualityRating/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupQualityRating.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupQualityRating/lookupQualityRating-detail.html',
                        controller: 'LookupQualityRatingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupQualityRating');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupQualityRating', function($stateParams, LookupQualityRating) {
                        return LookupQualityRating.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupQualityRating.new', {
                parent: 'lookupQualityRating',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupQualityRating/lookupQualityRating-dialog.html',
                        controller: 'LookupQualityRatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('lookupQualityRating', null, { reload: true });
                    }, function() {
                        $state.go('lookupQualityRating');
                    })
                }]
            })
            .state('lookupQualityRating.edit', {
                parent: 'lookupQualityRating',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupQualityRating/lookupQualityRating-dialog.html',
                        controller: 'LookupQualityRatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupQualityRating', function(LookupQualityRating) {
                                return LookupQualityRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupQualityRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupQualityRating.delete', {
                parent: 'lookupQualityRating',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupQualityRating/lookupQualityRating-delete-dialog.html',
                        controller: 'LookupQualityRatingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupQualityRating', function(LookupQualityRating) {
                                return LookupQualityRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupQualityRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
