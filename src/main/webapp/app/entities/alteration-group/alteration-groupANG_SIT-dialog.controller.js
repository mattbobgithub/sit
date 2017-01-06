(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationGroupAngSitDialogController', AlterationGroupAngSitDialogController);

    AlterationGroupAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AlterationGroup', 'AlterationSubGroup', 'Garment'];

    function AlterationGroupAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AlterationGroup, AlterationSubGroup, Garment) {
        var vm = this;

        vm.alterationGroup = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterationsubgroups = AlterationSubGroup.query();
        vm.garments = Garment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alterationGroup.id !== null) {
                AlterationGroup.update(vm.alterationGroup, onSaveSuccess, onSaveError);
            } else {
                AlterationGroup.save(vm.alterationGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:alterationGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
