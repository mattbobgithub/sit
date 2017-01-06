(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SizeTypeAngSitDeleteController',SizeTypeAngSitDeleteController);

    SizeTypeAngSitDeleteController.$inject = ['$uibModalInstance', 'entity', 'SizeType'];

    function SizeTypeAngSitDeleteController($uibModalInstance, entity, SizeType) {
        var vm = this;

        vm.sizeType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SizeType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
