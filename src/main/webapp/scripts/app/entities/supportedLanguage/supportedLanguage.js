'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supportedLanguage', {
                parent: 'entity',
                url: '/supportedLanguages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.supportedLanguage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supportedLanguage/supportedLanguages.html',
                        controller: 'SupportedLanguageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supportedLanguage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('supportedLanguage.detail', {
                parent: 'entity',
                url: '/supportedLanguage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.supportedLanguage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/supportedLanguage/supportedLanguage-detail.html',
                        controller: 'SupportedLanguageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('supportedLanguage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SupportedLanguage', function($stateParams, SupportedLanguage) {
                        return SupportedLanguage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('supportedLanguage.new', {
                parent: 'supportedLanguage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedLanguage/supportedLanguage-dialog.html',
                        controller: 'SupportedLanguageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('supportedLanguage', null, { reload: true });
                    }, function() {
                        $state.go('supportedLanguage');
                    })
                }]
            })
            .state('supportedLanguage.edit', {
                parent: 'supportedLanguage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedLanguage/supportedLanguage-dialog.html',
                        controller: 'SupportedLanguageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SupportedLanguage', function(SupportedLanguage) {
                                return SupportedLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('supportedLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('supportedLanguage.delete', {
                parent: 'supportedLanguage',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/supportedLanguage/supportedLanguage-delete-dialog.html',
                        controller: 'SupportedLanguageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SupportedLanguage', function(SupportedLanguage) {
                                return SupportedLanguage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('supportedLanguage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
