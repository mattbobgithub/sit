'use strict';

describe('Controller Tests', function() {

    describe('AlterationGroup Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlterationGroup, MockAlterationSubGroup, MockGarment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlterationGroup = jasmine.createSpy('MockAlterationGroup');
            MockAlterationSubGroup = jasmine.createSpy('MockAlterationSubGroup');
            MockGarment = jasmine.createSpy('MockGarment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AlterationGroup': MockAlterationGroup,
                'AlterationSubGroup': MockAlterationSubGroup,
                'Garment': MockGarment
            };
            createController = function() {
                $injector.get('$controller')("AlterationGroupAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:alterationGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
