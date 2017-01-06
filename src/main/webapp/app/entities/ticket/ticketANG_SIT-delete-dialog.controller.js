(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAngSitDeleteController',TicketAngSitDeleteController);

    TicketAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ticket'];

    function TicketAngSitDeleteController($uibModalInstance, entity, Ticket) {
        var vm = this;

        vm.ticket = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ticket.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
