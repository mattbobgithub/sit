(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketActionAngSitController', TicketActionAngSitController);

    TicketActionAngSitController.$inject = ['$scope', '$state', 'TicketAction'];

    function TicketActionAngSitController ($scope, $state, TicketAction) {
        var vm = this;

        vm.ticketActions = [];

        loadAll();

        function loadAll() {
            TicketAction.query(function(result) {
                vm.ticketActions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
