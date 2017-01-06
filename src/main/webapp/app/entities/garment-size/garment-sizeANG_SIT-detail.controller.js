(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('GarmentSizeAngSitDetailController', GarmentSizeAngSitDetailController);

    GarmentSizeAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GarmentSize', 'SizeType'];

    function GarmentSizeAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, GarmentSize, SizeType) {
        var vm = this;

        vm.garmentSize = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:garmentSizeUpdate', function(event, result) {
            vm.garmentSize = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
