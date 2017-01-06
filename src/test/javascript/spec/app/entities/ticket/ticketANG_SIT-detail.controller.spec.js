'use strict';

describe('Controller Tests', function() {

    describe('Ticket Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTicket, MockTicketAlteration, MockTicketAction, MockCustomer, MockStore, MockWorkroom, MockTransfer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTicket = jasmine.createSpy('MockTicket');
            MockTicketAlteration = jasmine.createSpy('MockTicketAlteration');
            MockTicketAction = jasmine.createSpy('MockTicketAction');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockStore = jasmine.createSpy('MockStore');
            MockWorkroom = jasmine.createSpy('MockWorkroom');
            MockTransfer = jasmine.createSpy('MockTransfer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ticket': MockTicket,
                'TicketAlteration': MockTicketAlteration,
                'TicketAction': MockTicketAction,
                'Customer': MockCustomer,
                'Store': MockStore,
                'Workroom': MockWorkroom,
                'Transfer': MockTransfer
            };
            createController = function() {
                $injector.get('$controller')("TicketAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:ticketUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
