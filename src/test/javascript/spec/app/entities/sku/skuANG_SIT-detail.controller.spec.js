'use strict';

describe('Controller Tests', function() {

    describe('Sku Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSku, MockGarmentSize, MockGarment, MockSizeType, MockColor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSku = jasmine.createSpy('MockSku');
            MockGarmentSize = jasmine.createSpy('MockGarmentSize');
            MockGarment = jasmine.createSpy('MockGarment');
            MockSizeType = jasmine.createSpy('MockSizeType');
            MockColor = jasmine.createSpy('MockColor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sku': MockSku,
                'GarmentSize': MockGarmentSize,
                'Garment': MockGarment,
                'SizeType': MockSizeType,
                'Color': MockColor
            };
            createController = function() {
                $injector.get('$controller')("SkuAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:skuUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
