(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sit-userANG_SIT', {
            parent: 'entity',
            url: '/sit-userANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SitUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sit-user/sit-usersANG_SIT.html',
                    controller: 'SitUserAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('sit-userANG_SIT-detail', {
            parent: 'entity',
            url: '/sit-userANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SitUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sit-user/sit-userANG_SIT-detail.html',
                    controller: 'SitUserAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SitUser', function($stateParams, SitUser) {
                    return SitUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sit-userANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sit-userANG_SIT-detail.edit', {
            parent: 'sit-userANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sit-user/sit-userANG_SIT-dialog.html',
                    controller: 'SitUserAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SitUser', function(SitUser) {
                            return SitUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sit-userANG_SIT.new', {
            parent: 'sit-userANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sit-user/sit-userANG_SIT-dialog.html',
                    controller: 'SitUserAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                email: null,
                                userType: null,
                                fitterIndicator: null,
                                managerApprovalCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sit-userANG_SIT', null, { reload: 'sit-userANG_SIT' });
                }, function() {
                    $state.go('sit-userANG_SIT');
                });
            }]
        })
        .state('sit-userANG_SIT.edit', {
            parent: 'sit-userANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sit-user/sit-userANG_SIT-dialog.html',
                    controller: 'SitUserAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SitUser', function(SitUser) {
                            return SitUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sit-userANG_SIT', null, { reload: 'sit-userANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sit-userANG_SIT.delete', {
            parent: 'sit-userANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sit-user/sit-userANG_SIT-delete-dialog.html',
                    controller: 'SitUserAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SitUser', function(SitUser) {
                            return SitUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sit-userANG_SIT', null, { reload: 'sit-userANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
