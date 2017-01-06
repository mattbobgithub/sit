(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('garment-sizeANG_SIT', {
            parent: 'entity',
            url: '/garment-sizeANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GarmentSizes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/garment-size/garment-sizesANG_SIT.html',
                    controller: 'GarmentSizeAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('garment-sizeANG_SIT-detail', {
            parent: 'entity',
            url: '/garment-sizeANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GarmentSize'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/garment-size/garment-sizeANG_SIT-detail.html',
                    controller: 'GarmentSizeAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GarmentSize', function($stateParams, GarmentSize) {
                    return GarmentSize.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'garment-sizeANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('garment-sizeANG_SIT-detail.edit', {
            parent: 'garment-sizeANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment-size/garment-sizeANG_SIT-dialog.html',
                    controller: 'GarmentSizeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GarmentSize', function(GarmentSize) {
                            return GarmentSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('garment-sizeANG_SIT.new', {
            parent: 'garment-sizeANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment-size/garment-sizeANG_SIT-dialog.html',
                    controller: 'GarmentSizeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('garment-sizeANG_SIT', null, { reload: 'garment-sizeANG_SIT' });
                }, function() {
                    $state.go('garment-sizeANG_SIT');
                });
            }]
        })
        .state('garment-sizeANG_SIT.edit', {
            parent: 'garment-sizeANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment-size/garment-sizeANG_SIT-dialog.html',
                    controller: 'GarmentSizeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GarmentSize', function(GarmentSize) {
                            return GarmentSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('garment-sizeANG_SIT', null, { reload: 'garment-sizeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('garment-sizeANG_SIT.delete', {
            parent: 'garment-sizeANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment-size/garment-sizeANG_SIT-delete-dialog.html',
                    controller: 'GarmentSizeAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GarmentSize', function(GarmentSize) {
                            return GarmentSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('garment-sizeANG_SIT', null, { reload: 'garment-sizeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
