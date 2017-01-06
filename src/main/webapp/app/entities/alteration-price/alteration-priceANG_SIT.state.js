(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alteration-priceANG_SIT', {
            parent: 'entity',
            url: '/alteration-priceANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlterationPrices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-price/alteration-pricesANG_SIT.html',
                    controller: 'AlterationPriceAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alteration-priceANG_SIT-detail', {
            parent: 'entity',
            url: '/alteration-priceANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlterationPrice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-price/alteration-priceANG_SIT-detail.html',
                    controller: 'AlterationPriceAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AlterationPrice', function($stateParams, AlterationPrice) {
                    return AlterationPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alteration-priceANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alteration-priceANG_SIT-detail.edit', {
            parent: 'alteration-priceANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-price/alteration-priceANG_SIT-dialog.html',
                    controller: 'AlterationPriceAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationPrice', function(AlterationPrice) {
                            return AlterationPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-priceANG_SIT.new', {
            parent: 'alteration-priceANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-price/alteration-priceANG_SIT-dialog.html',
                    controller: 'AlterationPriceAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alteration-priceANG_SIT', null, { reload: 'alteration-priceANG_SIT' });
                }, function() {
                    $state.go('alteration-priceANG_SIT');
                });
            }]
        })
        .state('alteration-priceANG_SIT.edit', {
            parent: 'alteration-priceANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-price/alteration-priceANG_SIT-dialog.html',
                    controller: 'AlterationPriceAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationPrice', function(AlterationPrice) {
                            return AlterationPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-priceANG_SIT', null, { reload: 'alteration-priceANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-priceANG_SIT.delete', {
            parent: 'alteration-priceANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-price/alteration-priceANG_SIT-delete-dialog.html',
                    controller: 'AlterationPriceAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AlterationPrice', function(AlterationPrice) {
                            return AlterationPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-priceANG_SIT', null, { reload: 'alteration-priceANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
