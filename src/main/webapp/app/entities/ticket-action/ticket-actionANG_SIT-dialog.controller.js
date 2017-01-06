(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketActionAngSitDialogController', TicketActionAngSitDialogController);

    TicketActionAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TicketAction', 'Ticket'];

    function TicketActionAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TicketAction, Ticket) {
        var vm = this;

        vm.ticketAction = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.ticketAction.id !== null) {
                TicketAction.update(vm.ticketAction, onSaveSuccess, onSaveError);
            } else {
                TicketAction.save(vm.ticketAction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:ticketActionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.actionDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
