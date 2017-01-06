(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationDisplayTypeAngSitDetailController', AlterationDisplayTypeAngSitDetailController);

    AlterationDisplayTypeAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AlterationDisplayType', 'Alteration'];

    function AlterationDisplayTypeAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, AlterationDisplayType, Alteration) {
        var vm = this;

        vm.alterationDisplayType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:alterationDisplayTypeUpdate', function(event, result) {
            vm.alterationDisplayType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
