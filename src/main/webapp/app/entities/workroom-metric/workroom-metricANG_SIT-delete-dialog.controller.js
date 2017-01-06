(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomMetricAngSitDeleteController',WorkroomMetricAngSitDeleteController);

    WorkroomMetricAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkroomMetric'];

    function WorkroomMetricAngSitDeleteController($uibModalInstance, entity, WorkroomMetric) {
        var vm = this;

        vm.workroomMetric = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkroomMetric.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
