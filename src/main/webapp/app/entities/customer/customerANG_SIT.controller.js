(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CustomerAngSitController', CustomerAngSitController);

    CustomerAngSitController.$inject = ['$scope', '$state', 'Customer'];

    function CustomerAngSitController ($scope, $state, Customer) {
        var vm = this;

        vm.customers = [];

        loadAll();

        function loadAll() {
            Customer.query(function(result) {
                vm.customers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
