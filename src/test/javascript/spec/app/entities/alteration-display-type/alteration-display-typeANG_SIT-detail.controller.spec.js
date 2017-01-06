'use strict';

describe('Controller Tests', function() {

    describe('AlterationDisplayType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlterationDisplayType, MockAlteration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlterationDisplayType = jasmine.createSpy('MockAlterationDisplayType');
            MockAlteration = jasmine.createSpy('MockAlteration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AlterationDisplayType': MockAlterationDisplayType,
                'Alteration': MockAlteration
            };
            createController = function() {
                $injector.get('$controller')("AlterationDisplayTypeAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:alterationDisplayTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
