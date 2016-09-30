'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('openSlot', {
                parent: 'entity',
                url: '/openSlots',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.openSlot.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/openSlot/openSlots.html',
                        controller: 'OpenSlotController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('openSlot');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('openSlot.detail', {
                parent: 'entity',
                url: '/openSlot/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.openSlot.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/openSlot/openSlot-detail.html',
                        controller: 'OpenSlotDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('openSlot');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'OpenSlot', function($stateParams, OpenSlot) {
                        return OpenSlot.get({id : $stateParams.id});
                    }]
                }
            })
            .state('openSlot.new', {
                parent: 'openSlot',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/openSlot/openSlot-dialog.html',
                        controller: 'OpenSlotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    openSlots: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('openSlot', null, { reload: true });
                    }, function() {
                        $state.go('openSlot');
                    })
                }]
            })
            .state('openSlot.edit', {
                parent: 'openSlot',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/openSlot/openSlot-dialog.html',
                        controller: 'OpenSlotDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['OpenSlot', function(OpenSlot) {
                                return OpenSlot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('openSlot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('openSlot.delete', {
                parent: 'openSlot',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/openSlot/openSlot-delete-dialog.html',
                        controller: 'OpenSlotDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['OpenSlot', function(OpenSlot) {
                                return OpenSlot.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('openSlot', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
