(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationSubGroupAngSitDeleteController',AlterationSubGroupAngSitDeleteController);

    AlterationSubGroupAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'AlterationSubGroup'];

    function AlterationSubGroupAngSitDeleteController($uibModalInstance, entity, AlterationSubGroup) {
        var vm = this;

        vm.alterationSubGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AlterationSubGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
