(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SitUserAngSitController', SitUserAngSitController);

    SitUserAngSitController.$inject = ['$scope', '$state', 'SitUser'];

    function SitUserAngSitController ($scope, $state, SitUser) {
        var vm = this;

        vm.sitUsers = [];

        loadAll();

        function loadAll() {
            SitUser.query(function(result) {
                vm.sitUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
