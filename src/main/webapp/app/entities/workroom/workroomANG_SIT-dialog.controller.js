(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomAngSitDialogController', WorkroomAngSitDialogController);

    WorkroomAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Workroom', 'WorkroomMetric', 'SitUser'];

    function WorkroomAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Workroom, WorkroomMetric, SitUser) {
        var vm = this;

        vm.workroom = entity;
        vm.clear = clear;
        vm.save = save;
        vm.workroommetrics = WorkroomMetric.query({filter: 'workroom-is-null'});
        $q.all([vm.workroom.$promise, vm.workroommetrics.$promise]).then(function() {
            if (!vm.workroom.workroomMetricId) {
                return $q.reject();
            }
            return WorkroomMetric.get({id : vm.workroom.workroomMetricId}).$promise;
        }).then(function(workroomMetric) {
            vm.workroommetrics.push(workroomMetric);
        });
        vm.situsers = SitUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workroom.id !== null) {
                Workroom.update(vm.workroom, onSaveSuccess, onSaveError);
            } else {
                Workroom.save(vm.workroom, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:workroomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
