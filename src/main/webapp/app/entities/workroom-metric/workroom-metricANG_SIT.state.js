(function() {
    'use strict';

    angular
        .module('sitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('workroom-metricANG_SIT', {
            parent: 'entity',
            url: '/workroom-metricANG_SIT',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkroomMetrics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workroom-metric/workroom-metricsANG_SIT.html',
                    controller: 'WorkroomMetricAngSitController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('workroom-metricANG_SIT-detail', {
            parent: 'entity',
            url: '/workroom-metricANG_SIT/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WorkroomMetric'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/workroom-metric/workroom-metricANG_SIT-detail.html',
                    controller: 'WorkroomMetricAngSitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WorkroomMetric', function($stateParams, WorkroomMetric) {
                    return WorkroomMetric.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'workroom-metricANG_SIT',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('workroom-metricANG_SIT-detail.edit', {
            parent: 'workroom-metricANG_SIT-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom-metric/workroom-metricANG_SIT-dialog.html',
                    controller: 'WorkroomMetricAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkroomMetric', function(WorkroomMetric) {
                            return WorkroomMetric.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workroom-metricANG_SIT.new', {
            parent: 'workroom-metricANG_SIT',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom-metric/workroom-metricANG_SIT-dialog.html',
                    controller: 'WorkroomMetricAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                defaultFittingTimeMins: null,
                                efficiencyPercentage: null,
                                utilizationPercentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('workroom-metricANG_SIT', null, { reload: 'workroom-metricANG_SIT' });
                }, function() {
                    $state.go('workroom-metricANG_SIT');
                });
            }]
        })
        .state('workroom-metricANG_SIT.edit', {
            parent: 'workroom-metricANG_SIT',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom-metric/workroom-metricANG_SIT-dialog.html',
                    controller: 'WorkroomMetricAngSitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkroomMetric', function(WorkroomMetric) {
                            return WorkroomMetric.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workroom-metricANG_SIT', null, { reload: 'workroom-metricANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('workroom-metricANG_SIT.delete', {
            parent: 'workroom-metricANG_SIT',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/workroom-metric/workroom-metricANG_SIT-delete-dialog.html',
                    controller: 'WorkroomMetricAngSitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkroomMetric', function(WorkroomMetric) {
                            return WorkroomMetric.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('workroom-metricANG_SIT', null, { reload: 'workroom-metricANG_SIT' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
