(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationSubGroupAngSitController', AlterationSubGroupAngSitController);

    AlterationSubGroupAngSitController.$inject = ['$scope', '$state', 'AlterationSubGroup'];

    function AlterationSubGroupAngSitController ($scope, $state, AlterationSubGroup) {
        var vm = this;

        vm.alterationSubGroups = [];

        loadAll();

        function loadAll() {
            AlterationSubGroup.query(function(result) {
                vm.alterationSubGroups = result;
                vm.searchQuery = null;
            });
        }
    }
})();
