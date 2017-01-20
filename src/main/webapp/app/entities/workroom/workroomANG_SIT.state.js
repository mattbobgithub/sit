(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('workroomANG_SIT', {
            parent: 'entity',
            url: '/workroomANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Workrooms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workroom/workroomsANG_SIT.html',
                    controller: 'WorkroomAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('workroomANG_SIT-detail', {
            parent: 'entity',
            url: '/workroomANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Workroom'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workroom/workroomANG_SIT-detail.html',
                    controller: 'WorkroomAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Workroom', function($stateParams, Workroom) {
                    return Workroom.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'workroomANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('workroomANG_SIT-detail.edit', {
            parent: 'workroomANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom/workroomANG_SIT-dialog.html',
                    controller: 'WorkroomAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Workroom', function(Workroom) {
                            return Workroom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workroomANG_SIT.new', {
            parent: 'workroomANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom/workroomANG_SIT-dialog.html',
                    controller: 'WorkroomAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                phone: null,
                                centralWorkroomIndicator: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('workroomANG_SIT', null, { reload: 'workroomANG_SIT' });
                }, function() {
                    $state.go('workroomANG_SIT');
                });
            }]
        })
        .state('workroomANG_SIT.edit', {
            parent: 'workroomANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom/workroomANG_SIT-dialog.html',
                    controller: 'WorkroomAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Workroom', function(Workroom) {
                            return Workroom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workroomANG_SIT', null, { reload: 'workroomANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workroomANG_SIT.delete', {
            parent: 'workroomANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom/workroomANG_SIT-delete-dialog.html',
                    controller: 'WorkroomAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Workroom', function(Workroom) {
                            return Workroom.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workroomANG_SIT', null, { reload: 'workroomANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
