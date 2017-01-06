'use strict';

describe('Controller Tests', function() {

    describe('AlterationSubGroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlterationSubGroup, MockAlterationGroup, MockAlteration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlterationSubGroup = jasmine.createSpy('MockAlterationSubGroup');
            MockAlterationGroup = jasmine.createSpy('MockAlterationGroup');
            MockAlteration = jasmine.createSpy('MockAlteration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AlterationSubGroup': MockAlterationSubGroup,
                'AlterationGroup': MockAlterationGroup,
                'Alteration': MockAlteration
            };
            createController = function() {
                $injector.get('$controller')("AlterationSubGroupAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:alterationSubGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
