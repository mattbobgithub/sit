(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('size-typeANG_SIT', {
            parent: 'entity',
            url: '/size-typeANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'SizeTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/size-type/size-typesANG_SIT.html',
                    controller: 'SizeTypeAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('size-typeANG_SIT-detail', {
            parent: 'entity',
            url: '/size-typeANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'SizeType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/size-type/size-typeANG_SIT-detail.html',
                    controller: 'SizeTypeAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SizeType', function($stateParams, SizeType) {
                    return SizeType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'size-typeANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('size-typeANG_SIT-detail.edit', {
            parent: 'size-typeANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/size-type/size-typeANG_SIT-dialog.html',
                    controller: 'SizeTypeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SizeType', function(SizeType) {
                            return SizeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('size-typeANG_SIT.new', {
            parent: 'size-typeANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/size-type/size-typeANG_SIT-dialog.html',
                    controller: 'SizeTypeAngSitDialogController',
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
                    $state.go('size-typeANG_SIT', null, { reload: 'size-typeANG_SIT' });
                }, function() {
                    $state.go('size-typeANG_SIT');
                });
            }]
        })
        .state('size-typeANG_SIT.edit', {
            parent: 'size-typeANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/size-type/size-typeANG_SIT-dialog.html',
                    controller: 'SizeTypeAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SizeType', function(SizeType) {
                            return SizeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('size-typeANG_SIT', null, { reload: 'size-typeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('size-typeANG_SIT.delete', {
            parent: 'size-typeANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/size-type/size-typeANG_SIT-delete-dialog.html',
                    controller: 'SizeTypeAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SizeType', function(SizeType) {
                            return SizeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('size-typeANG_SIT', null, { reload: 'size-typeANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
