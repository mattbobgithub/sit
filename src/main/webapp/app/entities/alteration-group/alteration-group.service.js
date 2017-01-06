(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('AlterationGroup', AlterationGroup);

    AlterationGroup.$inject = ['$resource'];

    function AlterationGroup ($resource) {
        var resourceUrl =  'api/alteration-groups/:id';

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
