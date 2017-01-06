(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('PriceCategory', PriceCategory);

    PriceCategory.$inject = ['$resource'];

    function PriceCategory ($resource) {
        var resourceUrl =  'api/price-categories/:id';

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
