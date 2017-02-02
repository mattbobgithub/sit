(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alteration-sub-groupANG_SIT', {
            parent: 'entity',
            url: '/alteration-sub-groupANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'AlterationSubGroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupsANG_SIT.html',
                    controller: 'AlterationSubGroupAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alteration-sub-groupANG_SIT-detail', {
            parent: 'entity',
            url: '/alteration-sub-groupANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'AlterationSubGroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupANG_SIT-detail.html',
                    controller: 'AlterationSubGroupAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AlterationSubGroup', function($stateParams, AlterationSubGroup) {
                    return AlterationSubGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alteration-sub-groupANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alteration-sub-groupANG_SIT-detail.edit', {
            parent: 'alteration-sub-groupANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupANG_SIT-dialog.html',
                    controller: 'AlterationSubGroupAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationSubGroup', function(AlterationSubGroup) {
                            return AlterationSubGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-sub-groupANG_SIT.new', {
            parent: 'alteration-sub-groupANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupANG_SIT-dialog.html',
                    controller: 'AlterationSubGroupAngSitDialogController',
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
                    $state.go('alteration-sub-groupANG_SIT', null, { reload: 'alteration-sub-groupANG_SIT' });
                }, function() {
                    $state.go('alteration-sub-groupANG_SIT');
                });
            }]
        })
        .state('alteration-sub-groupANG_SIT.edit', {
            parent: 'alteration-sub-groupANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupANG_SIT-dialog.html',
                    controller: 'AlterationSubGroupAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationSubGroup', function(AlterationSubGroup) {
                            return AlterationSubGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-sub-groupANG_SIT', null, { reload: 'alteration-sub-groupANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-sub-groupANG_SIT.delete', {
            parent: 'alteration-sub-groupANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-sub-group/alteration-sub-groupANG_SIT-delete-dialog.html',
                    controller: 'AlterationSubGroupAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AlterationSubGroup', function(AlterationSubGroup) {
                            return AlterationSubGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-sub-groupANG_SIT', null, { reload: 'alteration-sub-groupANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
