(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationPriceAngSitController', AlterationPriceAngSitController);

    AlterationPriceAngSitController.$inject = ['$scope', '$state', 'AlterationPrice'];

    function AlterationPriceAngSitController ($scope, $state, AlterationPrice) {
        var vm = this;

        vm.alterationPrices = [];

        loadAll();

        function loadAll() {
            AlterationPrice.query(function(result) {
                vm.alterationPrices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
