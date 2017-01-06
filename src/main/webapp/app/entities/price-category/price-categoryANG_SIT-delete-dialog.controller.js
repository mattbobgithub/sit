(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('PriceCategoryAngSitDeleteController',PriceCategoryAngSitDeleteController);

    PriceCategoryAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'PriceCategory'];

    function PriceCategoryAngSitDeleteController($uibModalInstance, entity, PriceCategory) {
        var vm = this;

        vm.priceCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PriceCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
