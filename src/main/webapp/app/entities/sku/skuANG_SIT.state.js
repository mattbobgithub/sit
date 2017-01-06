(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('skuANG_SIT', {
            parent: 'entity',
            url: '/skuANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Skus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku/skusANG_SIT.html',
                    controller: 'SkuAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('skuANG_SIT-detail', {
            parent: 'entity',
            url: '/skuANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sku'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sku/skuANG_SIT-detail.html',
                    controller: 'SkuAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sku', function($stateParams, Sku) {
                    return Sku.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'skuANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('skuANG_SIT-detail.edit', {
            parent: 'skuANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku/skuANG_SIT-dialog.html',
                    controller: 'SkuAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sku', function(Sku) {
                            return Sku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skuANG_SIT.new', {
            parent: 'skuANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku/skuANG_SIT-dialog.html',
                    controller: 'SkuAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                skuCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('skuANG_SIT', null, { reload: 'skuANG_SIT' });
                }, function() {
                    $state.go('skuANG_SIT');
                });
            }]
        })
        .state('skuANG_SIT.edit', {
            parent: 'skuANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku/skuANG_SIT-dialog.html',
                    controller: 'SkuAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sku', function(Sku) {
                            return Sku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skuANG_SIT', null, { reload: 'skuANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skuANG_SIT.delete', {
            parent: 'skuANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sku/skuANG_SIT-delete-dialog.html',
                    controller: 'SkuAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sku', function(Sku) {
                            return Sku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skuANG_SIT', null, { reload: 'skuANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
