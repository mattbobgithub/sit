'use strict';

describe('Controller Tests', function() {

    describe('Alteration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlteration, MockAlterationPrice, MockAlterationDisplayType, MockAlterationSubGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlteration = jasmine.createSpy('MockAlteration');
            MockAlterationPrice = jasmine.createSpy('MockAlterationPrice');
            MockAlterationDisplayType = jasmine.createSpy('MockAlterationDisplayType');
            MockAlterationSubGroup = jasmine.createSpy('MockAlterationSubGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Alteration': MockAlteration,
                'AlterationPrice': MockAlterationPrice,
                'AlterationDisplayType': MockAlterationDisplayType,
                'AlterationSubGroup': MockAlterationSubGroup
            };
            createController = function() {
                $injector.get('$controller')("AlterationAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:alterationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
