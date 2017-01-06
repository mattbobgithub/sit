(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationPriceAngSitDetailController', AlterationPriceAngSitDetailController);

    AlterationPriceAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AlterationPrice', 'PriceCategory', 'Alteration'];

    function AlterationPriceAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, AlterationPrice, PriceCategory, Alteration) {
        var vm = this;

        vm.alterationPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:alterationPriceUpdate', function(event, result) {
            vm.alterationPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
