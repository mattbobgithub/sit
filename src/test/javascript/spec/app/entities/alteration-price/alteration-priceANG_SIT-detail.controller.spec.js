'use strict';

describe('Controller Tests', function() {

    describe('AlterationPrice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlterationPrice, MockPriceCategory, MockAlteration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlterationPrice = jasmine.createSpy('MockAlterationPrice');
            MockPriceCategory = jasmine.createSpy('MockPriceCategory');
            MockAlteration = jasmine.createSpy('MockAlteration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AlterationPrice': MockAlterationPrice,
                'PriceCategory': MockPriceCategory,
                'Alteration': MockAlteration
            };
            createController = function() {
                $injector.get('$controller')("AlterationPriceAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:alterationPriceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
