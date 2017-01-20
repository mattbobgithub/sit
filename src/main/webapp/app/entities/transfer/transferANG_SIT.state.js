(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transferANG_SIT', {
            parent: 'entity',
            url: '/transferANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Transfers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transfer/transfersANG_SIT.html',
                    controller: 'TransferAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('transferANG_SIT-detail', {
            parent: 'entity',
            url: '/transferANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Transfer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transfer/transferANG_SIT-detail.html',
                    controller: 'TransferAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Transfer', function($stateParams, Transfer) {
                    return Transfer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'transferANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('transferANG_SIT-detail.edit', {
            parent: 'transferANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transfer/transferANG_SIT-dialog.html',
                    controller: 'TransferAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Transfer', function(Transfer) {
                            return Transfer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transferANG_SIT.new', {
            parent: 'transferANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transfer/transferANG_SIT-dialog.html',
                    controller: 'TransferAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fromWorkroomId: null,
                                toWorkroomId: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transferANG_SIT', null, { reload: 'transferANG_SIT' });
                }, function() {
                    $state.go('transferANG_SIT');
                });
            }]
        })
        .state('transferANG_SIT.edit', {
            parent: 'transferANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transfer/transferANG_SIT-dialog.html',
                    controller: 'TransferAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Transfer', function(Transfer) {
                            return Transfer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transferANG_SIT', null, { reload: 'transferANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transferANG_SIT.delete', {
            parent: 'transferANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transfer/transferANG_SIT-delete-dialog.html',
                    controller: 'TransferAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Transfer', function(Transfer) {
                            return Transfer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transferANG_SIT', null, { reload: 'transferANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
