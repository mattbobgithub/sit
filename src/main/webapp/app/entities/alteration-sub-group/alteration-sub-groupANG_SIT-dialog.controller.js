(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationSubGroupAngSitDialogController', AlterationSubGroupAngSitDialogController);

    AlterationSubGroupAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AlterationSubGroup', 'AlterationGroup', 'Alteration'];

    function AlterationSubGroupAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AlterationSubGroup, AlterationGroup, Alteration) {
        var vm = this;

        vm.alterationSubGroup = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterationgroups = AlterationGroup.query();
        vm.alterations = Alteration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alterationSubGroup.id !== null) {
                AlterationSubGroup.update(vm.alterationSubGroup, onSaveSuccess, onSaveError);
            } else {
                AlterationSubGroup.save(vm.alterationSubGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:alterationSubGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
