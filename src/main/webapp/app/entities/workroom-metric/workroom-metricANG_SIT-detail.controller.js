(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomMetricAngSitDetailController', WorkroomMetricAngSitDetailController);

    WorkroomMetricAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkroomMetric'];

    function WorkroomMetricAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkroomMetric) {
        var vm = this;

        vm.workroomMetric = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:workroomMetricUpdate', function(event, result) {
            vm.workroomMetric = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
