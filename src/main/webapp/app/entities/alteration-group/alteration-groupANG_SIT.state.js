(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alteration-groupANG_SIT', {
            parent: 'entity',
            url: '/alteration-groupANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'AlterationGroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-group/alteration-groupsANG_SIT.html',
                    controller: 'AlterationGroupAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alteration-groupANG_SIT-detail', {
            parent: 'entity',
            url: '/alteration-groupANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlterationGroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration-group/alteration-groupANG_SIT-detail.html',
                    controller: 'AlterationGroupAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AlterationGroup', function($stateParams, AlterationGroup) {
                    return AlterationGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alteration-groupANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alteration-groupANG_SIT-detail.edit', {
            parent: 'alteration-groupANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-group/alteration-groupANG_SIT-dialog.html',
                    controller: 'AlterationGroupAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationGroup', function(AlterationGroup) {
                            return AlterationGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-groupANG_SIT.new', {
            parent: 'alteration-groupANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-group/alteration-groupANG_SIT-dialog.html',
                    controller: 'AlterationGroupAngSitDialogController',
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
                    $state.go('alteration-groupANG_SIT', null, { reload: 'alteration-groupANG_SIT' });
                }, function() {
                    $state.go('alteration-groupANG_SIT');
                });
            }]
        })
        .state('alteration-groupANG_SIT.edit', {
            parent: 'alteration-groupANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-group/alteration-groupANG_SIT-dialog.html',
                    controller: 'AlterationGroupAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlterationGroup', function(AlterationGroup) {
                            return AlterationGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-groupANG_SIT', null, { reload: 'alteration-groupANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration-groupANG_SIT.delete', {
            parent: 'alteration-groupANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration-group/alteration-groupANG_SIT-delete-dialog.html',
                    controller: 'AlterationGroupAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AlterationGroup', function(AlterationGroup) {
                            return AlterationGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration-groupANG_SIT', null, { reload: 'alteration-groupANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
