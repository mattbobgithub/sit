(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationAngSitController', AlterationAngSitController);

    AlterationAngSitController.$inject = ['$scope', '$state', 'Alteration'];

    function AlterationAngSitController ($scope, $state, Alteration) {
        var vm = this;

        vm.alterations = [];

        loadAll();

        function loadAll() {
            Alteration.query(function(result) {
                vm.alterations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
