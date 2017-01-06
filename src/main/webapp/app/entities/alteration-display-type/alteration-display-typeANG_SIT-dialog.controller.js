(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationDisplayTypeAngSitDialogController', AlterationDisplayTypeAngSitDialogController);

    AlterationDisplayTypeAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AlterationDisplayType', 'Alteration'];

    function AlterationDisplayTypeAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AlterationDisplayType, Alteration) {
        var vm = this;

        vm.alterationDisplayType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterations = Alteration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alterationDisplayType.id !== null) {
                AlterationDisplayType.update(vm.alterationDisplayType, onSaveSuccess, onSaveError);
            } else {
                AlterationDisplayType.save(vm.alterationDisplayType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:alterationDisplayTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
