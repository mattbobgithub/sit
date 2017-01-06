(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomAngSitDetailController', WorkroomAngSitDetailController);

    WorkroomAngSitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Workroom', 'WorkroomMetric', 'SitUser'];

    function WorkroomAngSitDetailController($scope, $rootScope, $stateParams, previousState, entity, Workroom, WorkroomMetric, SitUser) {
        var vm = this;

        vm.workroom = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sitApp:workroomUpdate', function(event, result) {
            vm.workroom = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
