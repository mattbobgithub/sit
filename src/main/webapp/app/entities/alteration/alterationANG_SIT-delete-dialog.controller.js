(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationAngSitDeleteController',AlterationAngSitDeleteController);

    AlterationAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Alteration'];

    function AlterationAngSitDeleteController($uibModalInstance, entity, Alteration) {
        var vm = this;

        vm.alteration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Alteration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
