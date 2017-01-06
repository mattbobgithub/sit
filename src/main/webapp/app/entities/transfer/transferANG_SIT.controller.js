(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TransferAngSitController', TransferAngSitController);

    TransferAngSitController.$inject = ['$scope', '$state', 'Transfer'];

    function TransferAngSitController ($scope, $state, Transfer) {
        var vm = this;

        vm.transfers = [];

        loadAll();

        function loadAll() {
            Transfer.query(function(result) {
                vm.transfers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
