(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationGroupAngSitDeleteController',AlterationGroupAngSitDeleteController);

    AlterationGroupAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'AlterationGroup'];

    function AlterationGroupAngSitDeleteController($uibModalInstance, entity, AlterationGroup) {
        var vm = this;

        vm.alterationGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AlterationGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
