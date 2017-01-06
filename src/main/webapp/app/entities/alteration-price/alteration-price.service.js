(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('AlterationPrice', AlterationPrice);

    AlterationPrice.$inject = ['$resource'];

    function AlterationPrice ($resource) {
        var resourceUrl =  'api/alteration-prices/:id';

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
