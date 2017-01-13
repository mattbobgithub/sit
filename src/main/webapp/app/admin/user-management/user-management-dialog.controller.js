(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity',  'SitUser', 'Company', 'Store', 'Workroom', 'User'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity,  SitUser, Company, Store, Workroom, User) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;

        //MTC added for SitUser props
        vm.companies = Company.query();
        vm.stores = Store.query();
        vm.workrooms = Workroom.query();
        vm.users = User.query();

        console.log(vm);



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
