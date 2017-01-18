(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('colorANG_SIT', {
            parent: 'entity',
            url: '/colorANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Colors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/color/colorsANG_SIT.html',
                    controller: 'ColorAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('colorANG_SIT-detail', {
            parent: 'entity',
            url: '/colorANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Color'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/color/colorANG_SIT-detail.html',
                    controller: 'ColorAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Color', function($stateParams, Color) {
                    return Color.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'colorANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('colorANG_SIT-detail.edit', {
            parent: 'colorANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/color/colorANG_SIT-dialog.html',
                    controller: 'ColorAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Color', function(Color) {
                            return Color.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('colorANG_SIT.new', {
            parent: 'colorANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/color/colorANG_SIT-dialog.html',
                    controller: 'ColorAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                orderNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('colorANG_SIT', null, { reload: 'colorANG_SIT' });
                }, function() {
                    $state.go('colorANG_SIT');
                });
            }]
        })
        .state('colorANG_SIT.edit', {
            parent: 'colorANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/color/colorANG_SIT-dialog.html',
                    controller: 'ColorAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Color', function(Color) {
                            return Color.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('colorANG_SIT', null, { reload: 'colorANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('colorANG_SIT.delete', {
            parent: 'colorANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/color/colorANG_SIT-delete-dialog.html',
                    controller: 'ColorAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Color', function(Color) {
                            return Color.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('colorANG_SIT', null, { reload: 'colorANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
