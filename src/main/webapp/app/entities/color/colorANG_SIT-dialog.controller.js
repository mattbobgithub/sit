(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('ColorAngSitDialogController', ColorAngSitDialogController);

    ColorAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Color'];

    function ColorAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Color) {
        var vm = this;

        vm.color = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.color.id !== null) {
                Color.update(vm.color, onSaveSuccess, onSaveError);
            } else {
                Color.save(vm.color, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:colorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
