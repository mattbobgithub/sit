(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('WorkroomMetric', WorkroomMetric);

    WorkroomMetric.$inject = ['$resource'];

    function WorkroomMetric ($resource) {
        var resourceUrl =  'api/workroom-metrics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
