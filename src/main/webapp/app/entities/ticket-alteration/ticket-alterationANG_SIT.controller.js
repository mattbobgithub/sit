(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAlterationAngSitController', TicketAlterationAngSitController);

    TicketAlterationAngSitController.$inject = ['$scope', '$state', 'TicketAlteration'];

    function TicketAlterationAngSitController ($scope, $state, TicketAlteration) {
        var vm = this;

        vm.ticketAlterations = [];

        loadAll();

        function loadAll() {
            TicketAlteration.query(function(result) {
                vm.ticketAlterations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
