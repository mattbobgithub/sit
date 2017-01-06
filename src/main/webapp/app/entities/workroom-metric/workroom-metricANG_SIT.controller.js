(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomMetricAngSitController', WorkroomMetricAngSitController);

    WorkroomMetricAngSitController.$inject = ['$scope', '$state', 'WorkroomMetric'];

    function WorkroomMetricAngSitController ($scope, $state, WorkroomMetric) {
        var vm = this;

        vm.workroomMetrics = [];

        loadAll();

        function loadAll() {
            WorkroomMetric.query(function(result) {
                vm.workroomMetrics = result;
                vm.searchQuery = null;
            });
        }
    }
})();
