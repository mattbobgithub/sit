(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alterationANG_SIT', {
            parent: 'entity',
            url: '/alterationANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Alterations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration/alterationsANG_SIT.html',
                    controller: 'AlterationAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alterationANG_SIT-detail', {
            parent: 'entity',
            url: '/alterationANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Alteration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration/alterationANG_SIT-detail.html',
                    controller: 'AlterationAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Alteration', function($stateParams, Alteration) {
                    return Alteration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alterationANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alterationANG_SIT-detail.edit', {
            parent: 'alterationANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alterationANG_SIT-dialog.html',
                    controller: 'AlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alterationANG_SIT.new', {
            parent: 'alterationANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alterationANG_SIT-dialog.html',
                    controller: 'AlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                gender: null,
                                activeStatus: null,
                                shortDescription: null,
                                longDescription: null,
                                estimatedTime: null,
                                measurement1: null,
                                measurement2: null,
                                quantity: null,
                                shortListInd: null,
                                autoDefaultInd: null,
                                groupOrderNum: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alterationANG_SIT', null, { reload: 'alterationANG_SIT' });
                }, function() {
                    $state.go('alterationANG_SIT');
                });
            }]
        })
        .state('alterationANG_SIT.edit', {
            parent: 'alterationANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alterationANG_SIT-dialog.html',
                    controller: 'AlterationAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alterationANG_SIT', null, { reload: 'alterationANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alterationANG_SIT.delete', {
            parent: 'alterationANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alterationANG_SIT-delete-dialog.html',
                    controller: 'AlterationAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alterationANG_SIT', null, { reload: 'alterationANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
