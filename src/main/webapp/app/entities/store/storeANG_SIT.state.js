(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('storeANG_SIT', {
            parent: 'entity',
            url: '/storeANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Stores'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store/storesANG_SIT.html',
                    controller: 'StoreAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('storeANG_SIT-detail', {
            parent: 'entity',
            url: '/storeANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Store'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/store/storeANG_SIT-detail.html',
                    controller: 'StoreAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Store', function($stateParams, Store) {
                    return Store.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'storeANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('storeANG_SIT-detail.edit', {
            parent: 'storeANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store/storeANG_SIT-dialog.html',
                    controller: 'StoreAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Store', function(Store) {
                            return Store.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('storeANG_SIT.new', {
            parent: 'storeANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store/storeANG_SIT-dialog.html',
                    controller: 'StoreAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                phone: null,
                                defaultPromiseDays: null,
                                defaultPriceCategoryId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('storeANG_SIT', null, { reload: 'storeANG_SIT' });
                }, function() {
                    $state.go('storeANG_SIT');
                });
            }]
        })
        .state('storeANG_SIT.edit', {
            parent: 'storeANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store/storeANG_SIT-dialog.html',
                    controller: 'StoreAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Store', function(Store) {
                            return Store.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('storeANG_SIT', null, { reload: 'storeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('storeANG_SIT.delete', {
            parent: 'storeANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/store/storeANG_SIT-delete-dialog.html',
                    controller: 'StoreAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Store', function(Store) {
                            return Store.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('storeANG_SIT', null, { reload: 'storeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
