(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('garmentANG_SIT', {
            parent: 'entity',
            url: '/garmentANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Garments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/garment/garmentsANG_SIT.html',
                    controller: 'GarmentAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('garmentANG_SIT-detail', {
            parent: 'entity',
            url: '/garmentANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Garment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/garment/garmentANG_SIT-detail.html',
                    controller: 'GarmentAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Garment', function($stateParams, Garment) {
                    return Garment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'garmentANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('garmentANG_SIT-detail.edit', {
            parent: 'garmentANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment/garmentANG_SIT-dialog.html',
                    controller: 'GarmentAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Garment', function(Garment) {
                            return Garment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('garmentANG_SIT.new', {
            parent: 'garmentANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment/garmentANG_SIT-dialog.html',
                    controller: 'GarmentAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                gender: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('garmentANG_SIT', null, { reload: 'garmentANG_SIT' });
                }, function() {
                    $state.go('garmentANG_SIT');
                });
            }]
        })
        .state('garmentANG_SIT.edit', {
            parent: 'garmentANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment/garmentANG_SIT-dialog.html',
                    controller: 'GarmentAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Garment', function(Garment) {
                            return Garment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('garmentANG_SIT', null, { reload: 'garmentANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('garmentANG_SIT.delete', {
            parent: 'garmentANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/garment/garmentANG_SIT-delete-dialog.html',
                    controller: 'GarmentAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Garment', function(Garment) {
                            return Garment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('garmentANG_SIT', null, { reload: 'garmentANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
