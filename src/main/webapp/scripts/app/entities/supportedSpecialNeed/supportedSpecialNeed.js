'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supportedSpecialNeed', {
                parent: 'entity',
                url: '/supportedSpecialNeeds',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.supportedSpecialNeed.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supportedSpecialNeed/supportedSpecialNeeds.html',
                        controller: 'SupportedSpecialNeedController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supportedSpecialNeed');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('supportedSpecialNeed.detail', {
                parent: 'entity',
                url: '/supportedSpecialNeed/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.supportedSpecialNeed.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supportedSpecialNeed/supportedSpecialNeed-detail.html',
                        controller: 'SupportedSpecialNeedDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supportedSpecialNeed');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SupportedSpecialNeed', function($stateParams, SupportedSpecialNeed) {
                        return SupportedSpecialNeed.get({id : $stateParams.id});
                    }]
                }
            })
            .state('supportedSpecialNeed.new', {
                parent: 'supportedSpecialNeed',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedSpecialNeed/supportedSpecialNeed-dialog.html',
                        controller: 'SupportedSpecialNeedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('supportedSpecialNeed', null, { reload: true });
                    }, function() {
                        $state.go('supportedSpecialNeed');
                    })
                }]
            })
            .state('supportedSpecialNeed.edit', {
                parent: 'supportedSpecialNeed',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedSpecialNeed/supportedSpecialNeed-dialog.html',
                        controller: 'SupportedSpecialNeedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SupportedSpecialNeed', function(SupportedSpecialNeed) {
                                return SupportedSpecialNeed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('supportedSpecialNeed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('supportedSpecialNeed.delete', {
                parent: 'supportedSpecialNeed',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedSpecialNeed/supportedSpecialNeed-delete-dialog.html',
                        controller: 'SupportedSpecialNeedDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SupportedSpecialNeed', function(SupportedSpecialNeed) {
                                return SupportedSpecialNeed.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('supportedSpecialNeed', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
