'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupAgeGroups', {
                parent: 'entity',
                url: '/lookupAgeGroupss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupAgeGroups.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupAgeGroups/lookupAgeGroupss.html',
                        controller: 'LookupAgeGroupsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupAgeGroups');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupAgeGroups.detail', {
                parent: 'entity',
                url: '/lookupAgeGroups/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupAgeGroups.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupAgeGroups/lookupAgeGroups-detail.html',
                        controller: 'LookupAgeGroupsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupAgeGroups');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupAgeGroups', function($stateParams, LookupAgeGroups) {
                        return LookupAgeGroups.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupAgeGroups.new', {
                parent: 'lookupAgeGroups',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupAgeGroups/lookupAgeGroups-dialog.html',
                        controller: 'LookupAgeGroupsDialogController',
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
                        $state.go('lookupAgeGroups', null, { reload: true });
                    }, function() {
                        $state.go('lookupAgeGroups');
                    })
                }]
            })
            .state('lookupAgeGroups.edit', {
                parent: 'lookupAgeGroups',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupAgeGroups/lookupAgeGroups-dialog.html',
                        controller: 'LookupAgeGroupsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupAgeGroups', function(LookupAgeGroups) {
                                return LookupAgeGroups.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupAgeGroups', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupAgeGroups.delete', {
                parent: 'lookupAgeGroups',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupAgeGroups/lookupAgeGroups-delete-dialog.html',
                        controller: 'LookupAgeGroupsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupAgeGroups', function(LookupAgeGroups) {
                                return LookupAgeGroups.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupAgeGroups', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
