(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('AlterationSubGroupAngSitDetailController', AlterationSubGroupAngSitDetailController);

    AlterationSubGroupAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AlterationSubGroup', 'AlterationGroup', 'Alteration'];

    function AlterationSubGroupAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, AlterationSubGroup, AlterationGroup, Alteration) {
        var vm = this;

        vm.alterationSubGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:alterationSubGroupUpdate', function(event, result) {
            vm.alterationSubGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
