(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentSizeAngSitController', GarmentSizeAngSitController);

    GarmentSizeAngSitController.$inject = ['$scope', '$state', 'GarmentSize'];

    function GarmentSizeAngSitController ($scope, $state, GarmentSize) {
        var vm = this;

        vm.garmentSizes = [];

        loadAll();

        function loadAll() {
            GarmentSize.query(function(result) {
                vm.garmentSizes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
