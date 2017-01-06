(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('SizeType', SizeType);

    SizeType.$inject = ['$resource'];

    function SizeType ($resource) {
        var resourceUrl =  'api/size-types/:id';

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
