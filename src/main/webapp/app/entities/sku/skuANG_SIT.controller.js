(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SkuAngSitController', SkuAngSitController);

    SkuAngSitController.$inject = ['$scope', '$state', 'Sku'];

    function SkuAngSitController ($scope, $state, Sku) {
        var vm = this;

        vm.skus = [];

        loadAll();

        function loadAll() {
            Sku.query(function(result) {
                vm.skus = result;
                vm.searchQuery = null;
            });
        }
    }
})();
