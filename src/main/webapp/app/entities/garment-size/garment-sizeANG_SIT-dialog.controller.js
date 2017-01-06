(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentSizeAngSitDialogController', GarmentSizeAngSitDialogController);

    GarmentSizeAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GarmentSize', 'SizeType'];

    function GarmentSizeAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GarmentSize, SizeType) {
        var vm = this;

        vm.garmentSize = entity;
        vm.clear = clear;
        vm.save = save;
        vm.sizetypes = SizeType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.garmentSize.id !== null) {
                GarmentSize.update(vm.garmentSize, onSaveSuccess, onSaveError);
            } else {
                GarmentSize.save(vm.garmentSize, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:garmentSizeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
