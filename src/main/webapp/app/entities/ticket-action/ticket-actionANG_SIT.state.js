(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ticket-actionANG_SIT', {
            parent: 'entity',
            url: '/ticket-actionANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TicketActions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-action/ticket-actionsANG_SIT.html',
                    controller: 'TicketActionAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ticket-actionANG_SIT-detail', {
            parent: 'entity',
            url: '/ticket-actionANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TicketAction'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket-action/ticket-actionANG_SIT-detail.html',
                    controller: 'TicketActionAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TicketAction', function($stateParams, TicketAction) {
                    return TicketAction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticket-actionANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ticket-actionANG_SIT-detail.edit', {
            parent: 'ticket-actionANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-action/ticket-actionANG_SIT-dialog.html',
                    controller: 'TicketActionAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketAction', function(TicketAction) {
                            return TicketAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-actionANG_SIT.new', {
            parent: 'ticket-actionANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-action/ticket-actionANG_SIT-dialog.html',
                    controller: 'TicketActionAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                actionType: null,
                                actionDate: null,
                                actionBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ticket-actionANG_SIT', null, { reload: 'ticket-actionANG_SIT' });
                }, function() {
                    $state.go('ticket-actionANG_SIT');
                });
            }]
        })
        .state('ticket-actionANG_SIT.edit', {
            parent: 'ticket-actionANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-action/ticket-actionANG_SIT-dialog.html',
                    controller: 'TicketActionAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TicketAction', function(TicketAction) {
                            return TicketAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-actionANG_SIT', null, { reload: 'ticket-actionANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ticket-actionANG_SIT.delete', {
            parent: 'ticket-actionANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket-action/ticket-actionANG_SIT-delete-dialog.html',
                    controller: 'TicketActionAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TicketAction', function(TicketAction) {
                            return TicketAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticket-actionANG_SIT', null, { reload: 'ticket-actionANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
