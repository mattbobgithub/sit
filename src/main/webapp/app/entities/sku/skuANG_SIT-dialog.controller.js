(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SkuAngSitDialogController', SkuAngSitDialogController);

    SkuAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sku', 'GarmentSize', 'Garment', 'SizeType', 'Color'];

    function SkuAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sku, GarmentSize, Garment, SizeType, Color) {
        var vm = this;

        vm.sku = entity;
        vm.clear = clear;
        vm.save = save;
        vm.garmentsizes = GarmentSize.query();
        vm.garments = Garment.query();
        vm.sizetypes = SizeType.query();
        vm.colors = Color.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sku.id !== null) {
                Sku.update(vm.sku, onSaveSuccess, onSaveError);
            } else {
                Sku.save(vm.sku, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:skuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
