(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('PriceCategoryAngSitDialogController', PriceCategoryAngSitDialogController);

    PriceCategoryAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PriceCategory', 'AlterationPrice'];

    function PriceCategoryAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PriceCategory, AlterationPrice) {
        var vm = this;

        vm.priceCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterationprices = AlterationPrice.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.priceCategory.id !== null) {
                PriceCategory.update(vm.priceCategory, onSaveSuccess, onSaveError);
            } else {
                PriceCategory.save(vm.priceCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:priceCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
