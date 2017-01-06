(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentAngSitController', GarmentAngSitController);

    GarmentAngSitController.$inject = ['$scope', '$state', 'Garment'];

    function GarmentAngSitController ($scope, $state, Garment) {
        var vm = this;

        vm.garments = [];

        loadAll();

        function loadAll() {
            Garment.query(function(result) {
                vm.garments = result;
                vm.searchQuery = null;
            });
        }
    }
})();
