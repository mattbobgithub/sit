(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAlterationAngSitDeleteController',TicketAlterationAngSitDeleteController);

    TicketAlterationAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'TicketAlteration'];

    function TicketAlterationAngSitDeleteController($uibModalInstance, entity, TicketAlteration) {
        var vm = this;

        vm.ticketAlteration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TicketAlteration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
