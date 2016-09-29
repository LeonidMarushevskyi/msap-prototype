'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupSpecialNeedType', {
                parent: 'entity',
                url: '/lookupSpecialNeedTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupSpecialNeedType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedType/lookupSpecialNeedTypes.html',
                        controller: 'LookupSpecialNeedTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupSpecialNeedType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupSpecialNeedType.detail', {
                parent: 'entity',
                url: '/lookupSpecialNeedType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupSpecialNeedType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedType/lookupSpecialNeedType-detail.html',
                        controller: 'LookupSpecialNeedTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupSpecialNeedType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupSpecialNeedType', function($stateParams, LookupSpecialNeedType) {
                        return LookupSpecialNeedType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupSpecialNeedType.new', {
                parent: 'lookupSpecialNeedType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedType/lookupSpecialNeedType-dialog.html',
                        controller: 'LookupSpecialNeedTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    groupCode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('lookupSpecialNeedType', null, { reload: true });
                    }, function() {
                        $state.go('lookupSpecialNeedType');
                    })
                }]
            })
            .state('lookupSpecialNeedType.edit', {
                parent: 'lookupSpecialNeedType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedType/lookupSpecialNeedType-dialog.html',
                        controller: 'LookupSpecialNeedTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupSpecialNeedType', function(LookupSpecialNeedType) {
                                return LookupSpecialNeedType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupSpecialNeedType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupSpecialNeedType.delete', {
                parent: 'lookupSpecialNeedType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupSpecialNeedType/lookupSpecialNeedType-delete-dialog.html',
                        controller: 'LookupSpecialNeedTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupSpecialNeedType', function(LookupSpecialNeedType) {
                                return LookupSpecialNeedType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupSpecialNeedType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
