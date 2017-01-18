(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alteration-display-typeANG_SIT', {
            parent: 'entity',
            url: '/alteration-display-typeANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'AlterationDisplayTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typesANG_SIT.html',
                    controller: 'AlterationDisplayTypeAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alteration-display-typeANG_SIT-detail', {
            parent: 'entity',
            url: '/alteration-display-typeANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlterationDisplayType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typeANG_SIT-detail.html',
                    controller: 'AlterationDisplayTypeAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AlterationDisplayType', function($stateParams, AlterationDisplayType) {
                    return AlterationDisplayType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alteration-display-typeANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alteration-display-typeANG_SIT-detail.edit', {
            parent: 'alteration-display-typeANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typeANG_SIT-dialog.html',
                    controller: 'AlterationDisplayTypeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationDisplayType', function(AlterationDisplayType) {
                            return AlterationDisplayType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-display-typeANG_SIT.new', {
            parent: 'alteration-display-typeANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typeANG_SIT-dialog.html',
                    controller: 'AlterationDisplayTypeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                measurementType: null,
                                displayprice: null,
                                displaytime: null,
                                displayquantity: null,
                                displaymeasurement1: null,
                                displaymeasurement2: null,
                                enableprice: null,
                                enabletime: null,
                                enablequantity: null,
                                enablemeasurement1: null,
                                enablemeasurement2: null,
                                defaultPrice: null,
                                defaultTime: null,
                                defaultQuantity: null,
                                defaultMeasurement1: null,
                                defaultMeasurement2: null,
                                maxMeasurement1: null,
                                maxMeasurement2: null,
                                minMeasurement1: null,
                                minMeasurement2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alteration-display-typeANG_SIT', null, { reload: 'alteration-display-typeANG_SIT' });
                }, function() {
                    $state.go('alteration-display-typeANG_SIT');
                });
            }]
        })
        .state('alteration-display-typeANG_SIT.edit', {
            parent: 'alteration-display-typeANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typeANG_SIT-dialog.html',
                    controller: 'AlterationDisplayTypeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationDisplayType', function(AlterationDisplayType) {
                            return AlterationDisplayType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-display-typeANG_SIT', null, { reload: 'alteration-display-typeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-display-typeANG_SIT.delete', {
            parent: 'alteration-display-typeANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-display-type/alteration-display-typeANG_SIT-delete-dialog.html',
                    controller: 'AlterationDisplayTypeAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AlterationDisplayType', function(AlterationDisplayType) {
                            return AlterationDisplayType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-display-typeANG_SIT', null, { reload: 'alteration-display-typeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
