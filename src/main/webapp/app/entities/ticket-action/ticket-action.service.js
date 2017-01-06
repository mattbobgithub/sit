(function() {
    'use strict';
    angular
        .module('sitApp')
        .factory('TicketAction', TicketAction);

    TicketAction.$inject = ['$resource', 'DateUtils'];

    function TicketAction ($resource, DateUtils) {
        var resourceUrl =  'api/ticket-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.actionDate = DateUtils.convertDateTimeFromServer(data.actionDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
