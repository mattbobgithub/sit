(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentAngSitDetailController', GarmentAngSitDetailController);

    GarmentAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Garment', 'AlterationGroup', 'SizeType'];

    function GarmentAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Garment, AlterationGroup, SizeType) {
        var vm = this;

        vm.garment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:garmentUpdate', function(event, result) {
            vm.garment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
