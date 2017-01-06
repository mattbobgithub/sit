(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SitUserAngSitDeleteController',SitUserAngSitDeleteController);

    SitUserAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'SitUser'];

    function SitUserAngSitDeleteController($uibModalInstance, entity, SitUser) {
        var vm = this;

        vm.sitUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SitUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
