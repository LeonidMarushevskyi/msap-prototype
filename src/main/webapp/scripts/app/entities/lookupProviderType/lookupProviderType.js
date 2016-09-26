'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupProviderType', {
                parent: 'entity',
                url: '/lookupProviderTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupProviderType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupProviderType/lookupProviderTypes.html',
                        controller: 'LookupProviderTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupProviderType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupProviderType.detail', {
                parent: 'entity',
                url: '/lookupProviderType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupProviderType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupProviderType/lookupProviderType-detail.html',
                        controller: 'LookupProviderTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupProviderType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupProviderType', function($stateParams, LookupProviderType) {
                        return LookupProviderType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupProviderType.new', {
                parent: 'lookupProviderType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupProviderType/lookupProviderType-dialog.html',
                        controller: 'LookupProviderTypeDialogController',
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
                        $state.go('lookupProviderType', null, { reload: true });
                    }, function() {
                        $state.go('lookupProviderType');
                    })
                }]
            })
            .state('lookupProviderType.edit', {
                parent: 'lookupProviderType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupProviderType/lookupProviderType-dialog.html',
                        controller: 'LookupProviderTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupProviderType', function(LookupProviderType) {
                                return LookupProviderType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupProviderType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupProviderType.delete', {
                parent: 'lookupProviderType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupProviderType/lookupProviderType-delete-dialog.html',
                        controller: 'LookupProviderTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupProviderType', function(LookupProviderType) {
                                return LookupProviderType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupProviderType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
