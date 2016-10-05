'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupSpecialNeedGroup', {
                parent: 'entity',
                url: '/lookupSpecialNeedGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupSpecialNeedGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedGroup/lookupSpecialNeedGroups.html',
                        controller: 'LookupSpecialNeedGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupSpecialNeedGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupSpecialNeedGroup.detail', {
                parent: 'entity',
                url: '/lookupSpecialNeedGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupSpecialNeedGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedGroup/lookupSpecialNeedGroup-detail.html',
                        controller: 'LookupSpecialNeedGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupSpecialNeedGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupSpecialNeedGroup', function($stateParams, LookupSpecialNeedGroup) {
                        return LookupSpecialNeedGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupSpecialNeedGroup.new', {
                parent: 'lookupSpecialNeedGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedGroup/lookupSpecialNeedGroup-dialog.html',
                        controller: 'LookupSpecialNeedGroupDialogController',
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
                        $state.go('lookupSpecialNeedGroup', null, { reload: true });
                    }, function() {
                        $state.go('lookupSpecialNeedGroup');
                    })
                }]
            })
            .state('lookupSpecialNeedGroup.edit', {
                parent: 'lookupSpecialNeedGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedGroup/lookupSpecialNeedGroup-dialog.html',
                        controller: 'LookupSpecialNeedGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupSpecialNeedGroup', function(LookupSpecialNeedGroup) {
                                return LookupSpecialNeedGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupSpecialNeedGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupSpecialNeedGroup.delete', {
                parent: 'lookupSpecialNeedGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedGroup/lookupSpecialNeedGroup-delete-dialog.html',
                        controller: 'LookupSpecialNeedGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupSpecialNeedGroup', function(LookupSpecialNeedGroup) {
                                return LookupSpecialNeedGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupSpecialNeedGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
