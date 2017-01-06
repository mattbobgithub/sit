(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('ColorAngSitDetailController', ColorAngSitDetailController);

    ColorAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Color'];

    function ColorAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Color) {
        var vm = this;

        vm.color = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:colorUpdate', function(event, result) {
            vm.color = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
