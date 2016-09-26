'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupLicenseType', {
                parent: 'entity',
                url: '/lookupLicenseTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupLicenseType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupLicenseType/lookupLicenseTypes.html',
                        controller: 'LookupLicenseTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupLicenseType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupLicenseType.detail', {
                parent: 'entity',
                url: '/lookupLicenseType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupLicenseType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupLicenseType/lookupLicenseType-detail.html',
                        controller: 'LookupLicenseTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupLicenseType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupLicenseType', function($stateParams, LookupLicenseType) {
                        return LookupLicenseType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupLicenseType.new', {
                parent: 'lookupLicenseType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLicenseType/lookupLicenseType-dialog.html',
                        controller: 'LookupLicenseTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('lookupLicenseType', null, { reload: true });
                    }, function() {
                        $state.go('lookupLicenseType');
                    })
                }]
            })
            .state('lookupLicenseType.edit', {
                parent: 'lookupLicenseType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLicenseType/lookupLicenseType-dialog.html',
                        controller: 'LookupLicenseTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupLicenseType', function(LookupLicenseType) {
                                return LookupLicenseType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupLicenseType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupLicenseType.delete', {
                parent: 'lookupLicenseType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLicenseType/lookupLicenseType-delete-dialog.html',
                        controller: 'LookupLicenseTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupLicenseType', function(LookupLicenseType) {
                                return LookupLicenseType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupLicenseType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
