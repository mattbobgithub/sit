(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('SizeTypeAngSitDetailController', SizeTypeAngSitDetailController);

    SizeTypeAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SizeType', 'Garment', 'GarmentSize'];

    function SizeTypeAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, SizeType, Garment, GarmentSize) {
        var vm = this;

        vm.sizeType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:sizeTypeUpdate', function(event, result) {
            vm.sizeType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
