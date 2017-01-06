(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('StoreAngSitDetailController', StoreAngSitDetailController);

    StoreAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Store', 'SitUser', 'Workroom'];

    function StoreAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Store, SitUser, Workroom) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:storeUpdate', function(event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
