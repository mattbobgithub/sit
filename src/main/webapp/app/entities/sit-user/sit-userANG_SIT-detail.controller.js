(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SitUserAngSitDetailController', SitUserAngSitDetailController);

    SitUserAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SitUser', 'Company', 'Store', 'Workroom', 'User'];

    function SitUserAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, SitUser, Company, Store, Workroom, User) {
        var vm = this;

        vm.sitUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:sitUserUpdate', function(event, result) {
            vm.sitUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
