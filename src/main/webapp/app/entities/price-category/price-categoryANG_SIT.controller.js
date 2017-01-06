(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('PriceCategoryAngSitController', PriceCategoryAngSitController);

    PriceCategoryAngSitController.$inject = ['$scope', '$state', 'PriceCategory'];

    function PriceCategoryAngSitController ($scope, $state, PriceCategory) {
        var vm = this;

        vm.priceCategories = [];

        loadAll();

        function loadAll() {
            PriceCategory.query(function(result) {
                vm.priceCategories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
