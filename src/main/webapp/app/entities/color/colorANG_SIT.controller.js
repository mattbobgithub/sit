(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('ColorAngSitController', ColorAngSitController);

    ColorAngSitController.$inject = ['$scope', '$state', 'Color'];

    function ColorAngSitController ($scope, $state, Color) {
        var vm = this;

        vm.colors = [];

        loadAll();

        function loadAll() {
            Color.query(function(result) {
                vm.colors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
