'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupLanguage', {
                parent: 'entity',
                url: '/lookupLanguages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupLanguage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupLanguage/lookupLanguages.html',
                        controller: 'LookupLanguageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupLanguage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupLanguage.detail', {
                parent: 'entity',
                url: '/lookupLanguage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupLanguage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupLanguage/lookupLanguage-detail.html',
                        controller: 'LookupLanguageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupLanguage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupLanguage', function($stateParams, LookupLanguage) {
                        return LookupLanguage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupLanguage.new', {
                parent: 'lookupLanguage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLanguage/lookupLanguage-dialog.html',
                        controller: 'LookupLanguageDialogController',
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
                        $state.go('lookupLanguage', null, { reload: true });
                    }, function() {
                        $state.go('lookupLanguage');
                    })
                }]
            })
            .state('lookupLanguage.edit', {
                parent: 'lookupLanguage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLanguage/lookupLanguage-dialog.html',
                        controller: 'LookupLanguageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupLanguage', function(LookupLanguage) {
                                return LookupLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupLanguage.delete', {
                parent: 'lookupLanguage',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupLanguage/lookupLanguage-delete-dialog.html',
                        controller: 'LookupLanguageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupLanguage', function(LookupLanguage) {
                                return LookupLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
