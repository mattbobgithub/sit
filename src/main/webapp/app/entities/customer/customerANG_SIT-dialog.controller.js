(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CustomerAngSitDialogController', CustomerAngSitDialogController);

    CustomerAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customer', 'CustomerAddress'];

    function CustomerAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Customer, CustomerAddress) {
        var vm = this;

        vm.customer = entity;
        console.log(vm.customer);

        vm.clear = clear;
        vm.save = save;
      //  vm.customeraddresses = CustomerAddress.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
