'use strict';

describe('Controller Tests', function() {

    describe('TicketAlteration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTicketAlteration, MockTicket;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTicketAlteration = jasmine.createSpy('MockTicketAlteration');
            MockTicket = jasmine.createSpy('MockTicket');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TicketAlteration': MockTicketAlteration,
                'Ticket': MockTicket
            };
            createController = function() {
                $injector.get('$controller')("TicketAlterationAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:ticketAlterationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
