(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SkuAngSitDeleteController',SkuAngSitDeleteController);

    SkuAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sku'];

    function SkuAngSitDeleteController($uibModalInstance, entity, Sku) {
        var vm = this;

        vm.sku = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sku.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
