(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationDisplayTypeAngSitController', AlterationDisplayTypeAngSitController);

    AlterationDisplayTypeAngSitController.$inject = ['$scope', '$state', 'AlterationDisplayType'];

    function AlterationDisplayTypeAngSitController ($scope, $state, AlterationDisplayType) {
        var vm = this;

        vm.alterationDisplayTypes = [];

        loadAll();

        function loadAll() {
            AlterationDisplayType.query(function(result) {
                vm.alterationDisplayTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
