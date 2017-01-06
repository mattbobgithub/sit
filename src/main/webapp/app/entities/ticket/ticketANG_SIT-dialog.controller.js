(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAngSitDialogController', TicketAngSitDialogController);

    TicketAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ticket', 'TicketAlteration', 'TicketAction', 'Customer', 'Store', 'Workroom', 'Transfer'];

    function TicketAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ticket, TicketAlteration, TicketAction, Customer, Store, Workroom, Transfer) {
        var vm = this;

        vm.ticket = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ticketalterations = TicketAlteration.query();
        vm.ticketactions = TicketAction.query();
        vm.customers = Customer.query();
        vm.stores = Store.query();
        vm.workrooms = Workroom.query();
        vm.transfers = Transfer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ticket.id !== null) {
                Ticket.update(vm.ticket, onSaveSuccess, onSaveError);
            } else {
                Ticket.save(vm.ticket, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:ticketUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dropDate = false;
        vm.datePickerOpenStatus.promiseDate = false;
        vm.datePickerOpenStatus.workroomDeadline = false;
        vm.datePickerOpenStatus.completedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
