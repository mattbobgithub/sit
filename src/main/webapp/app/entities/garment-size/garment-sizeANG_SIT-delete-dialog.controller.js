(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentSizeAngSitDeleteController',GarmentSizeAngSitDeleteController);

    GarmentSizeAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'GarmentSize'];

    function GarmentSizeAngSitDeleteController($uibModalInstance, entity, GarmentSize) {
        var vm = this;

        vm.garmentSize = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GarmentSize.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
