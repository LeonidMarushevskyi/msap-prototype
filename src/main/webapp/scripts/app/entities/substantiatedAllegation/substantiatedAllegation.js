'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('substantiatedAllegation', {
                parent: 'entity',
                url: '/substantiatedAllegations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.substantiatedAllegation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/substantiatedAllegation/substantiatedAllegations.html',
                        controller: 'SubstantiatedAllegationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('substantiatedAllegation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('substantiatedAllegation.detail', {
                parent: 'entity',
                url: '/substantiatedAllegation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.substantiatedAllegation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/substantiatedAllegation/substantiatedAllegation-detail.html',
                        controller: 'SubstantiatedAllegationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('substantiatedAllegation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SubstantiatedAllegation', function($stateParams, SubstantiatedAllegation) {
                        return SubstantiatedAllegation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('substantiatedAllegation.new', {
                parent: 'substantiatedAllegation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/substantiatedAllegation/substantiatedAllegation-dialog.html',
                        controller: 'SubstantiatedAllegationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    severity: null,
                                    allegationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('substantiatedAllegation', null, { reload: true });
                    }, function() {
                        $state.go('substantiatedAllegation');
                    })
                }]
            })
            .state('substantiatedAllegation.edit', {
                parent: 'substantiatedAllegation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/substantiatedAllegation/substantiatedAllegation-dialog.html',
                        controller: 'SubstantiatedAllegationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SubstantiatedAllegation', function(SubstantiatedAllegation) {
                                return SubstantiatedAllegation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('substantiatedAllegation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('substantiatedAllegation.delete', {
                parent: 'substantiatedAllegation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/substantiatedAllegation/substantiatedAllegation-delete-dialog.html',
                        controller: 'SubstantiatedAllegationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SubstantiatedAllegation', function(SubstantiatedAllegation) {
                                return SubstantiatedAllegation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('substantiatedAllegation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
