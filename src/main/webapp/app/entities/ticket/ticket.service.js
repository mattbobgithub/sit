(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('Ticket', Ticket);

    Ticket.$inject = ['$resource', 'DateUtils'];

    function Ticket ($resource, DateUtils) {
        var resourceUrl =  'api/tickets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dropDate = DateUtils.convertDateTimeFromServer(data.dropDate);
                        data.promiseDate = DateUtils.convertDateTimeFromServer(data.promiseDate);
                        data.workroomDeadline = DateUtils.convertDateTimeFromServer(data.workroomDeadline);
                        data.completedDate = DateUtils.convertDateTimeFromServer(data.completedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
