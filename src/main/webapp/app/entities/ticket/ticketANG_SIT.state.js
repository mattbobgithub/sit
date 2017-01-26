(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ticketANG_SIT', {
            parent: 'entity',
            url: '/ticketANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Tickets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket/ticketsANG_SIT.html',
                    controller: 'TicketAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ticketANG_SIT-detail', {
            parent: 'entity',
            url: '/ticketANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Ticket'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket/ticketANG_SIT-detail.html',
                    controller: 'TicketAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ticket', function($stateParams, Ticket) {
                    return Ticket.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticketANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ticketANG_SIT-detail.edit', {
            parent: 'ticketANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticketANG_SIT-dialog.html',
                    controller: 'TicketAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ticket', function(Ticket) {
                            return Ticket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        // .state('ticketANG_SIT.new', {
        //     parent: 'ticketANG_SIT',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/ticket/ticketANG_SIT-dialog.html',
        //             controller: 'TicketAngSitDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         dropDate: null,
        //                         promiseDate: null,
        //                         workroomDeadline: null,
        //                         recieptId: null,
        //                         priceCharged: null,
        //                         purchaseType: null,
        //                         priority: null,
        //                         completedBy: null,
        //                         completedDate: null,
        //                         waiveCostIndicator: null,
        //                         waiveCostReason: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('ticketANG_SIT', null, { reload: 'ticketANG_SIT' });
        //         }, function() {
        //             $state.go('ticketANG_SIT');
        //         });
        //     }]
        // })
        // .state('ticketANG_SIT.edit', {
        //     parent: 'ticketANG_SIT',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/ticket/ticketANG_SIT-dialog.html',
        //             controller: 'TicketAngSitDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Ticket', function(Ticket) {
        //                     return Ticket.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('ticketANG_SIT', null, { reload: 'ticketANG_SIT' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('ticketANG_SIT.delete', {
            parent: 'ticketANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ticket/ticketANG_SIT-delete-dialog.html',
                    controller: 'TicketAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ticket', function(Ticket) {
                            return Ticket.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ticketANG_SIT', null, { reload: 'ticketANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })

        //MTC - override new ticket and edit ticket
    .state('ticketANG_SIT.new', {
            parent: 'entity',
            url: '/ticketANG_SIT/0',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Ticket'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ticket/ticketNewEdit.html',
                    controller: 'TicketNewEditController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ticket', function($stateParams, Ticket) {
                    return Ticket.get({id : 0}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ticketANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
            .state('ticketANG_SIT.edit', {
                parent: 'entity',
                url: '/ticketANG_SIT/{id}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                    pageTitle: 'Ticket'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/ticket/ticketANG_SIT-detail.html',
                        controller: 'TicketAngSitDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Ticket', function($stateParams, Ticket) {
                        return Ticket.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'ticketANG_SIT',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            });
    }

})();
