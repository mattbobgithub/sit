(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('GarmentSize', GarmentSize);

    GarmentSize.$inject = ['$resource'];

    function GarmentSize ($resource) {
        var resourceUrl =  'api/garment-sizes/:id';

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
