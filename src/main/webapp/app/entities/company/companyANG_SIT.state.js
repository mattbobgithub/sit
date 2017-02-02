(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('companyANG_SIT', {
            parent: 'entity',
            url: '/companyANG_SIT',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Companies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company/companiesANG_SIT.html',
                    controller: 'CompanyAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('companyANG_SIT-detail', {
            parent: 'entity',
            url: '/companyANG_SIT/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Company'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company/companyANG_SIT-detail.html',
                    controller: 'CompanyAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Company', function($stateParams, Company) {
                    return Company.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'companyANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('companyANG_SIT-detail.edit', {
            parent: 'companyANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/companyANG_SIT-dialog.html',
                    controller: 'CompanyAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Company', function(Company) {
                            return Company.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('companyANG_SIT.new', {
            parent: 'companyANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/companyANG_SIT-dialog.html',
                    controller: 'CompanyAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                companyDbname: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('companyANG_SIT', null, { reload: 'companyANG_SIT' });
                }, function() {
                    $state.go('companyANG_SIT');
                });
            }]
        })
        .state('companyANG_SIT.edit', {
            parent: 'companyANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/companyANG_SIT-dialog.html',
                    controller: 'CompanyAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Company', function(Company) {
                            return Company.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('companyANG_SIT', null, { reload: 'companyANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('companyANG_SIT.delete', {
            parent: 'companyANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company/companyANG_SIT-delete-dialog.html',
                    controller: 'CompanyAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Company', function(Company) {
                            return Company.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('companyANG_SIT', null, { reload: 'companyANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
