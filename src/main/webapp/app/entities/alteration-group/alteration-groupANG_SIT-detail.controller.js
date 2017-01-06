(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationGroupAngSitDetailController', AlterationGroupAngSitDetailController);

    AlterationGroupAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AlterationGroup', 'AlterationSubGroup', 'Garment'];

    function AlterationGroupAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, AlterationGroup, AlterationSubGroup, Garment) {
        var vm = this;

        vm.alterationGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:alterationGroupUpdate', function(event, result) {
            vm.alterationGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
