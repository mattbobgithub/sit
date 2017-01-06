(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('StoreAngSitController', StoreAngSitController);

    StoreAngSitController.$inject = ['$scope', '$state', 'Store'];

    function StoreAngSitController ($scope, $state, Store) {
        var vm = this;

        vm.stores = [];

        loadAll();

        function loadAll() {
            Store.query(function(result) {
                vm.stores = result;
                vm.searchQuery = null;
            });
        }
    }
})();
