(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CompanyAngSitController', CompanyAngSitController);

    CompanyAngSitController.$inject = ['$scope', '$state', 'Company'];

    function CompanyAngSitController ($scope, $state, Company) {
        var vm = this;

        vm.companies = [];

        loadAll();

        function loadAll() {
            Company.query(function(result) {
                vm.companies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
