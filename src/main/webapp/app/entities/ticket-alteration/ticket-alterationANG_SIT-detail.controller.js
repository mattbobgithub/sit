(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TicketAlterationAngSitDetailController', TicketAlterationAngSitDetailController);

    TicketAlterationAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TicketAlteration', 'Ticket'];

    function TicketAlterationAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, TicketAlteration, Ticket) {
        var vm = this;

        vm.ticketAlteration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:ticketAlterationUpdate', function(event, result) {
            vm.ticketAlteration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
