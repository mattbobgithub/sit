(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationAngSitDialogController', AlterationAngSitDialogController);

    AlterationAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alteration', 'AlterationPrice', 'AlterationDisplayType', 'AlterationSubGroup'];

    function AlterationAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Alteration, AlterationPrice, AlterationDisplayType, AlterationSubGroup) {
        var vm = this;

        vm.alteration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterationprices = AlterationPrice.query();
        vm.alterationdisplaytypes = AlterationDisplayType.query();
        vm.alterationsubgroups = AlterationSubGroup.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alteration.id !== null) {
                Alteration.update(vm.alteration, onSaveSuccess, onSaveError);
            } else {
                Alteration.save(vm.alteration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:alterationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
