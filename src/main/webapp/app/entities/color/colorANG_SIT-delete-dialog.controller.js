(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('ColorAngSitDeleteController',ColorAngSitDeleteController);

    ColorAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Color'];

    function ColorAngSitDeleteController($uibModalInstance, entity, Color) {
        var vm = this;

        vm.color = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Color.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
