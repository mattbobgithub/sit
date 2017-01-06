'use strict';

describe('Controller Tests', function() {

    describe('Garment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGarment, MockAlterationGroup, MockSizeType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGarment = jasmine.createSpy('MockGarment');
            MockAlterationGroup = jasmine.createSpy('MockAlterationGroup');
            MockSizeType = jasmine.createSpy('MockSizeType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Garment': MockGarment,
                'AlterationGroup': MockAlterationGroup,
                'SizeType': MockSizeType
            };
            createController = function() {
                $injector.get('$controller')("GarmentAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:garmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
