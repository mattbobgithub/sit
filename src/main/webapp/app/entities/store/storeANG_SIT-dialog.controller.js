(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('StoreAngSitDialogController', StoreAngSitDialogController);

    StoreAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Store', 'SitUser', 'Workroom'];

    function StoreAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Store, SitUser, Workroom) {
        var vm = this;

        vm.store = entity;
        vm.clear = clear;
        vm.save = save;
        vm.situsers = SitUser.query();
        vm.workrooms = Workroom.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.store.id !== null) {
                Store.update(vm.store, onSaveSuccess, onSaveError);
            } else {
                Store.save(vm.store, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:storeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
