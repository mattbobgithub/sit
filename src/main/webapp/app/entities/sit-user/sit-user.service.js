(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('SitUser', SitUser);

    SitUser.$inject = ['$resource'];

    function SitUser ($resource) {
        var resourceUrl =  'api/sit-users/:id';

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
