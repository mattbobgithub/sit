(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('price-categoryANG_SIT', {
            parent: 'entity',
            url: '/price-categoryANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'PriceCategories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-category/price-categoriesANG_SIT.html',
                    controller: 'PriceCategoryAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('price-categoryANG_SIT-detail', {
            parent: 'entity',
            url: '/price-categoryANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'PriceCategory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-category/price-categoryANG_SIT-detail.html',
                    controller: 'PriceCategoryAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PriceCategory', function($stateParams, PriceCategory) {
                    return PriceCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'price-categoryANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('price-categoryANG_SIT-detail.edit', {
            parent: 'price-categoryANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-category/price-categoryANG_SIT-dialog.html',
                    controller: 'PriceCategoryAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PriceCategory', function(PriceCategory) {
                            return PriceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-categoryANG_SIT.new', {
            parent: 'price-categoryANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-category/price-categoryANG_SIT-dialog.html',
                    controller: 'PriceCategoryAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                percentageDiscount: null,
                                amountDiscount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('price-categoryANG_SIT', null, { reload: 'price-categoryANG_SIT' });
                }, function() {
                    $state.go('price-categoryANG_SIT');
                });
            }]
        })
        .state('price-categoryANG_SIT.edit', {
            parent: 'price-categoryANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-category/price-categoryANG_SIT-dialog.html',
                    controller: 'PriceCategoryAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PriceCategory', function(PriceCategory) {
                            return PriceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-categoryANG_SIT', null, { reload: 'price-categoryANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-categoryANG_SIT.delete', {
            parent: 'price-categoryANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-category/price-categoryANG_SIT-delete-dialog.html',
                    controller: 'PriceCategoryAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PriceCategory', function(PriceCategory) {
                            return PriceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-categoryANG_SIT', null, { reload: 'price-categoryANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
