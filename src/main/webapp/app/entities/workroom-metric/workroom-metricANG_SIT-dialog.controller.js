(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomMetricAngSitDialogController', WorkroomMetricAngSitDialogController);

    WorkroomMetricAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkroomMetric'];

    function WorkroomMetricAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkroomMetric) {
        var vm = this;

        vm.workroomMetric = entity;
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
            if (vm.workroomMetric.id !== null) {
                WorkroomMetric.update(vm.workroomMetric, onSaveSuccess, onSaveError);
            } else {
                WorkroomMetric.save(vm.workroomMetric, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:workroomMetricUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
