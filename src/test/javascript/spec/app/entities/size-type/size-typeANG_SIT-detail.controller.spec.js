'use strict';

describe('Controller Tests', function() {

    describe('SizeType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSizeType, MockGarment, MockGarmentSize;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSizeType = jasmine.createSpy('MockSizeType');
            MockGarment = jasmine.createSpy('MockGarment');
            MockGarmentSize = jasmine.createSpy('MockGarmentSize');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SizeType': MockSizeType,
                'Garment': MockGarment,
                'GarmentSize': MockGarmentSize
            };
            createController = function() {
                $injector.get('$controller')("SizeTypeAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:sizeTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
