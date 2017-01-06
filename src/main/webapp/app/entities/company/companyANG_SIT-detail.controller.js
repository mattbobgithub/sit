(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('CompanyAngSitDetailController', CompanyAngSitDetailController);

    CompanyAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Company', 'SitUser'];

    function CompanyAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Company, SitUser) {
        var vm = this;

        vm.company = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:companyUpdate', function(event, result) {
            vm.company = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
