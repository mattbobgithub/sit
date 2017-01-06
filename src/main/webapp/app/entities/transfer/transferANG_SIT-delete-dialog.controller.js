(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TransferAngSitDeleteController',TransferAngSitDeleteController);

    TransferAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Transfer'];

    function TransferAngSitDeleteController($uibModalInstance, entity, Transfer) {
        var vm = this;

        vm.transfer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Transfer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
