(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentAngSitDeleteController',GarmentAngSitDeleteController);

    GarmentAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Garment'];

    function GarmentAngSitDeleteController($uibModalInstance, entity, Garment) {
        var vm = this;

        vm.garment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Garment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
