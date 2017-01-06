(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('AlterationDisplayType', AlterationDisplayType);

    AlterationDisplayType.$inject = ['$resource'];

    function AlterationDisplayType ($resource) {
        var resourceUrl =  'api/alteration-display-types/:id';

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
