(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SitUserAngSitDialogController', SitUserAngSitDialogController);

    SitUserAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'SitUser', 'Company', 'Store', 'Workroom', 'User'];

    function SitUserAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, SitUser, Company, Store, Workroom, User) {
        var vm = this;

        vm.sitUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.companies = Company.query();
        vm.stores = Store.query();
        vm.workrooms = Workroom.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sitUser.id !== null) {
                SitUser.update(vm.sitUser, onSaveSuccess, onSaveError);
            } else {
                SitUser.save(vm.sitUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:sitUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
