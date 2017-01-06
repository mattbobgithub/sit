(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationPriceAngSitDialogController', AlterationPriceAngSitDialogController);

    AlterationPriceAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AlterationPrice', 'PriceCategory', 'Alteration'];

    function AlterationPriceAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AlterationPrice, PriceCategory, Alteration) {
        var vm = this;

        vm.alterationPrice = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pricecategories = PriceCategory.query();
        vm.alterations = Alteration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alterationPrice.id !== null) {
                AlterationPrice.update(vm.alterationPrice, onSaveSuccess, onSaveError);
            } else {
                AlterationPrice.save(vm.alterationPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:alterationPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
