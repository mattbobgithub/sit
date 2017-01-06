(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAngSitController', TicketAngSitController);

    TicketAngSitController.$inject = ['$scope', '$state', 'Ticket'];

    function TicketAngSitController ($scope, $state, Ticket) {
        var vm = this;

        vm.tickets = [];

        loadAll();

        function loadAll() {
            Ticket.query(function(result) {
                vm.tickets = result;
                vm.searchQuery = null;
            });
        }
    }
})();
