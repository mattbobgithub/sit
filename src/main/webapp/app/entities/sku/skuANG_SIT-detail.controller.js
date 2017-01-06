(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SkuAngSitDetailController', SkuAngSitDetailController);

    SkuAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sku', 'GarmentSize', 'Garment', 'SizeType', 'Color'];

    function SkuAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Sku, GarmentSize, Garment, SizeType, Color) {
        var vm = this;

        vm.sku = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:skuUpdate', function(event, result) {
            vm.sku = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
