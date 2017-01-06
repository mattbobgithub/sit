(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TransferAngSitDialogController', TransferAngSitDialogController);

    TransferAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Transfer', 'Ticket'];

    function TransferAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Transfer, Ticket) {
        var vm = this;

        vm.transfer = entity;
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
            if (vm.transfer.id !== null) {
                Transfer.update(vm.transfer, onSaveSuccess, onSaveError);
            } else {
                Transfer.save(vm.transfer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:transferUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
