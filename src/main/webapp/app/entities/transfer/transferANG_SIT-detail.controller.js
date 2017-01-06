(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('TransferAngSitDetailController', TransferAngSitDetailController);

    TransferAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Transfer', 'Ticket'];

    function TransferAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Transfer, Ticket) {
        var vm = this;

        vm.transfer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:transferUpdate', function(event, result) {
            vm.transfer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
