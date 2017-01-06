(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CustomerAngSitDeleteController',CustomerAngSitDeleteController);

    CustomerAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Customer'];

    function CustomerAngSitDeleteController($uibModalInstance, entity, Customer) {
        var vm = this;

        vm.customer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Customer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
