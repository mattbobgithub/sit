(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentAngSitDialogController', GarmentAngSitDialogController);

    GarmentAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Garment', 'AlterationGroup', 'SizeType'];

    function GarmentAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Garment, AlterationGroup, SizeType) {
        var vm = this;

        vm.garment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterationgroups = AlterationGroup.query();
        vm.sizetypes = SizeType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.garment.id !== null) {
                Garment.update(vm.garment, onSaveSuccess, onSaveError);
            } else {
                Garment.save(vm.garment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:garmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
