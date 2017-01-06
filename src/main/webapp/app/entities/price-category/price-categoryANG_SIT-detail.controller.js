(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('PriceCategoryAngSitDetailController', PriceCategoryAngSitDetailController);

    PriceCategoryAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PriceCategory', 'AlterationPrice'];

    function PriceCategoryAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, PriceCategory, AlterationPrice) {
        var vm = this;

        vm.priceCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:priceCategoryUpdate', function(event, result) {
            vm.priceCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
