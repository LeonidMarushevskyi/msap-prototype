'use strict';

angular.module('msapApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lookupDayOfWeek', {
                parent: 'entity',
                url: '/lookupDayOfWeeks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupDayOfWeek.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupDayOfWeek/lookupDayOfWeeks.html',
                        controller: 'LookupDayOfWeekController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupDayOfWeek');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('lookupDayOfWeek.detail', {
                parent: 'entity',
                url: '/lookupDayOfWeek/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'msapApp.lookupDayOfWeek.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lookupDayOfWeek/lookupDayOfWeek-detail.html',
                        controller: 'LookupDayOfWeekDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lookupDayOfWeek');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LookupDayOfWeek', function($stateParams, LookupDayOfWeek) {
                        return LookupDayOfWeek.get({id : $stateParams.id});
                    }]
                }
            })
            .state('lookupDayOfWeek.new', {
                parent: 'lookupDayOfWeek',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupDayOfWeek/lookupDayOfWeek-dialog.html',
                        controller: 'LookupDayOfWeekDialogController',
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
                        $state.go('lookupDayOfWeek', null, { reload: true });
                    }, function() {
                        $state.go('lookupDayOfWeek');
                    })
                }]
            })
            .state('lookupDayOfWeek.edit', {
                parent: 'lookupDayOfWeek',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupDayOfWeek/lookupDayOfWeek-dialog.html',
                        controller: 'LookupDayOfWeekDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LookupDayOfWeek', function(LookupDayOfWeek) {
                                return LookupDayOfWeek.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupDayOfWeek', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lookupDayOfWeek.delete', {
                parent: 'lookupDayOfWeek',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lookupDayOfWeek/lookupDayOfWeek-delete-dialog.html',
                        controller: 'LookupDayOfWeekDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LookupDayOfWeek', function(LookupDayOfWeek) {
                                return LookupDayOfWeek.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function() {
                        $state.go('lookupDayOfWeek', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
