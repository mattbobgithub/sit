(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketActionAngSitDetailController', TicketActionAngSitDetailController);

    TicketActionAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TicketAction', 'Ticket'];

    function TicketActionAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, TicketAction, Ticket) {
        var vm = this;

        vm.ticketAction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:ticketActionUpdate', function(event, result) {
            vm.ticketAction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
