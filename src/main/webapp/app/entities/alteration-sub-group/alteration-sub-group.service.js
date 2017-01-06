(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('AlterationSubGroup', AlterationSubGroup);

    AlterationSubGroup.$inject = ['$resource'];

    function AlterationSubGroup ($resource) {
        var resourceUrl =  'api/alteration-sub-groups/:id';

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
