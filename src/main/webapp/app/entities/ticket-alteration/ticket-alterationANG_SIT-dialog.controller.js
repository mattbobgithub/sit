(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAlterationAngSitDialogController', TicketAlterationAngSitDialogController);

    TicketAlterationAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TicketAlteration', 'Ticket'];

    function TicketAlterationAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TicketAlteration, Ticket) {
        var vm = this;

        vm.ticketAlteration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tickets = Ticket.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ticketAlteration.id !== null) {
                TicketAlteration.update(vm.ticketAlteration, onSaveSuccess, onSaveError);
            } else {
                TicketAlteration.save(vm.ticketAlteration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:ticketAlterationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
