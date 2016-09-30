'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('provider', {
                parent: 'entity',
                url: '/providers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.provider.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provider/providers.html',
                        controller: 'ProviderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provider');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('provider.detail', {
                parent: 'entity',
                url: '/provider/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.provider.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/provider/provider-detail.html',
                        controller: 'ProviderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('provider');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Provider', function($stateParams, Provider) {
                        return Provider.get({id : $stateParams.id});
                    }]
                }
            })
            .state('provider.new', {
                parent: 'provider',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provider/provider-dialog.html',
                        controller: 'ProviderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    providerCapacity: null,
                                    providerName: null,
                                    phoneNumber: null,
                                    isOpenOvernight: null,
                                    numberOfComplains: null,
                                    numberOfVisits: null,
                                    lastVisit: null,
                                    description: null,
                                    isFullDay: null,
                                    isAfterSchool: null,
                                    isBeforeSchool: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('provider', null, { reload: true });
                    }, function() {
                        $state.go('provider');
                    })
                }]
            })
            .state('provider.edit', {
                parent: 'provider',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provider/provider-dialog.html',
                        controller: 'ProviderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Provider', function(Provider) {
                                return Provider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('provider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('provider.delete', {
                parent: 'provider',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/provider/provider-delete-dialog.html',
                        controller: 'ProviderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Provider', function(Provider) {
                                return Provider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('provider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
