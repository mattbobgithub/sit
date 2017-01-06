(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAngSitDetailController', TicketAngSitDetailController);

    TicketAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ticket', 'TicketAlteration', 'TicketAction', 'Customer', 'Store', 'Workroom', 'Transfer'];

    function TicketAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Ticket, TicketAlteration, TicketAction, Customer, Store, Workroom, Transfer) {
        var vm = this;

        vm.ticket = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:ticketUpdate', function(event, result) {
            vm.ticket = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
