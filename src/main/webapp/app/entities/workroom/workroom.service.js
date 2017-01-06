(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('Workroom', Workroom);

    Workroom.$inject = ['$resource'];

    function Workroom ($resource) {
        var resourceUrl =  'api/workrooms/:id';

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
