(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SizeTypeAngSitController', SizeTypeAngSitController);

    SizeTypeAngSitController.$inject = ['$scope', '$state', 'SizeType'];

    function SizeTypeAngSitController ($scope, $state, SizeType) {
        var vm = this;

        vm.sizeTypes = [];

        loadAll();

        function loadAll() {
            SizeType.query(function(result) {
                vm.sizeTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
