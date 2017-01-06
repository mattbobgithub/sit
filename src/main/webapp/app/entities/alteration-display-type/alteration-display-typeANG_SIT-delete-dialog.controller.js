(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationDisplayTypeAngSitDeleteController',AlterationDisplayTypeAngSitDeleteController);

    AlterationDisplayTypeAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'AlterationDisplayType'];

    function AlterationDisplayTypeAngSitDeleteController($uibModalInstance, entity, AlterationDisplayType) {
        var vm = this;

        vm.alterationDisplayType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AlterationDisplayType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
