(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationGroupAngSitController', AlterationGroupAngSitController);

    AlterationGroupAngSitController.$inject = ['$scope', '$state', 'AlterationGroup'];

    function AlterationGroupAngSitController ($scope, $state, AlterationGroup) {
        var vm = this;

        vm.alterationGroups = [];

        loadAll();

        function loadAll() {
            AlterationGroup.query(function(result) {
                vm.alterationGroups = result;
                vm.searchQuery = null;
            });
        }
    }
})();
