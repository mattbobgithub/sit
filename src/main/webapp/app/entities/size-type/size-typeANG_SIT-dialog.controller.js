(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SizeTypeAngSitDialogController', SizeTypeAngSitDialogController);

    SizeTypeAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SizeType', 'Garment', 'GarmentSize'];

    function SizeTypeAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SizeType, Garment, GarmentSize) {
        var vm = this;

        vm.sizeType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.garments = Garment.query();
        vm.garmentsizes = GarmentSize.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sizeType.id !== null) {
                SizeType.update(vm.sizeType, onSaveSuccess, onSaveError);
            } else {
                SizeType.save(vm.sizeType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:sizeTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
