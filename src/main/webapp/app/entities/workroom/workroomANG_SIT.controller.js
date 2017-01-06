(function() {
    'use strict';

    angular
        .module('sitApp')
        .controller('WorkroomAngSitController', WorkroomAngSitController);

    WorkroomAngSitController.$inject = ['$scope', '$state', 'Workroom'];

    function WorkroomAngSitController ($scope, $state, Workroom) {
        var vm = this;

        vm.workrooms = [];

        loadAll();

        function loadAll() {
            Workroom.query(function(result) {
                vm.workrooms = result;
                vm.searchQuery = null;
            });
        }
    }
})();
