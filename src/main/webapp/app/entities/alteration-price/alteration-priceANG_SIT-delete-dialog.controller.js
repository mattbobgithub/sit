(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationPriceAngSitDeleteController',AlterationPriceAngSitDeleteController);

    AlterationPriceAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'AlterationPrice'];

    function AlterationPriceAngSitDeleteController($uibModalInstance, entity, AlterationPrice) {
        var vm = this;

        vm.alterationPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AlterationPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
