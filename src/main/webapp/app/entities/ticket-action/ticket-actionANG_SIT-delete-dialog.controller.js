(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketActionAngSitDeleteController',TicketActionAngSitDeleteController);

    TicketActionAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'TicketAction'];

    function TicketActionAngSitDeleteController($uibModalInstance, entity, TicketAction) {
        var vm = this;

        vm.ticketAction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TicketAction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
