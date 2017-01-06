(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationAngSitDetailController', AlterationAngSitDetailController);

    AlterationAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alteration', 'AlterationPrice', 'AlterationDisplayType', 'AlterationSubGroup'];

    function AlterationAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Alteration, AlterationPrice, AlterationDisplayType, AlterationSubGroup) {
        var vm = this;

        vm.alteration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:alterationUpdate', function(event, result) {
            vm.alteration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
