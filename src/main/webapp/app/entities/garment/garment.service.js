(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('Garment', Garment);

    Garment.$inject = ['$resource'];

    function Garment ($resource) {
        var resourceUrl =  'api/garments/:id';

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
