(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomAngSitDeleteController',WorkroomAngSitDeleteController);

    WorkroomAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Workroom'];

    function WorkroomAngSitDeleteController($uibModalInstance, entity, Workroom) {
        var vm = this;

        vm.workroom = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Workroom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
