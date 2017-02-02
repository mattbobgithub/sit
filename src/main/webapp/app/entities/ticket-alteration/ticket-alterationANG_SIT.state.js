(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ticket-alterationANG_SIT', {
            parent: 'entity',
            url: '/ticket-alterationANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'TicketAlterations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationsANG_SIT.html',
                    controller: 'TicketAlterationAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ticket-alterationANG_SIT-detail', {
            parent: 'entity',
            url: '/ticket-alterationANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'TicketAlteration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationANG_SIT-detail.html',
                    controller: 'TicketAlterationAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TicketAlteration', function($stateParams, TicketAlteration) {
                    return TicketAlteration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticket-alterationANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ticket-alterationANG_SIT-detail.edit', {
            parent: 'ticket-alterationANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationANG_SIT-dialog.html',
                    controller: 'TicketAlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketAlteration', function(TicketAlteration) {
                            return TicketAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-alterationANG_SIT.new', {
            parent: 'ticket-alterationANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationANG_SIT-dialog.html',
                    controller: 'TicketAlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lineNumber: null,
                                gender: null,
                                altId: null,
                                description: null,
                                tagType: null,
                                estimateTime: null,
                                price: null,
                                measurement1: null,
                                measurement2: null,
                                quantity: null,
                                completeIndicator: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ticket-alterationANG_SIT', null, { reload: 'ticket-alterationANG_SIT' });
                }, function() {
                    $state.go('ticket-alterationANG_SIT');
                });
            }]
        })
        .state('ticket-alterationANG_SIT.edit', {
            parent: 'ticket-alterationANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationANG_SIT-dialog.html',
                    controller: 'TicketAlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketAlteration', function(TicketAlteration) {
                            return TicketAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-alterationANG_SIT', null, { reload: 'ticket-alterationANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-alterationANG_SIT.delete', {
            parent: 'ticket-alterationANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-alteration/ticket-alterationANG_SIT-delete-dialog.html',
                    controller: 'TicketAlterationAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TicketAlteration', function(TicketAlteration) {
                            return TicketAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-alterationANG_SIT', null, { reload: 'ticket-alterationANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
