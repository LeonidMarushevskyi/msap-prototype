'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('review', {
                parent: 'entity',
                url: '/reviews',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.review.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/review/reviews.html',
                        controller: 'ReviewController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('review');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('review.detail', {
                parent: 'entity',
                url: '/review/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.review.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/review/review-detail.html',
                        controller: 'ReviewDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('review');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Review', function($stateParams, Review) {
                        return Review.get({id : $stateParams.id});
                    }]
                }
            })
            .state('review.new', {
                parent: 'review',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/review/review-dialog.html',
                        controller: 'ReviewDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    content: null,
                                    reviewDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('review', null, { reload: true });
                    }, function() {
                        $state.go('review');
                    })
                }]
            })
            .state('review.edit', {
                parent: 'review',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/review/review-dialog.html',
                        controller: 'ReviewDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Review', function(Review) {
                                return Review.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('review', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('review.delete', {
                parent: 'review',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/review/review-delete-dialog.html',
                        controller: 'ReviewDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Review', function(Review) {
                                return Review.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('review', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
