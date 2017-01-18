(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customerANG_SIT', {
            parent: 'entity',
            url: '/customerANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                pageTitle: 'Customers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer/customersANG_SIT.html',
                    controller: 'CustomerAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('customerANG_SIT-detail', {
            parent: 'entity',
            url: '/customerANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Customer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer/customerANG_SIT-detail.html',
                    controller: 'CustomerAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Customer', function($stateParams, Customer) {
                    return Customer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customerANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customerANG_SIT-detail.edit', {
            parent: 'customerANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerANG_SIT-dialog.html',
                    controller: 'CustomerAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customerANG_SIT.new', {
            parent: 'customerANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerANG_SIT-dialog.html',
                    controller: 'CustomerAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                customerCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customerANG_SIT', null, { reload: 'customerANG_SIT' });
                }, function() {
                    $state.go('customerANG_SIT');
                });
            }]
        })
        .state('customerANG_SIT.edit', {
            parent: 'customerANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerANG_SIT-dialog.html',
                    controller: 'CustomerAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customerANG_SIT', null, { reload: 'customerANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customerANG_SIT.delete', {
            parent: 'customerANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer/customerANG_SIT-delete-dialog.html',
                    controller: 'CustomerAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Customer', function(Customer) {
                            return Customer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customerANG_SIT', null, { reload: 'customerANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
