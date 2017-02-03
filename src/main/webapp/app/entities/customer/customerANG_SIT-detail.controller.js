(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CustomerAngSitDetailController', CustomerAngSitDetailController);

    CustomerAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customer', 'CustomerAddress'];

    function CustomerAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Customer, CustomerAddress) {
        var vm = this;

        vm.customer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
