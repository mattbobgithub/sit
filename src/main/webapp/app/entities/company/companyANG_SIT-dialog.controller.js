(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CompanyAngSitDialogController', CompanyAngSitDialogController);

    CompanyAngSitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Company', 'SitUser'];

    function CompanyAngSitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Company, SitUser) {
        var vm = this;

        vm.company = entity;
        vm.clear = clear;
        vm.save = save;
        vm.situsers = SitUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.company.id !== null) {
                Company.update(vm.company, onSaveSuccess, onSaveError);
            } else {
                Company.save(vm.company, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sitApp:companyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
