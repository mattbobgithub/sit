'use strict';

describe('Controller Tests', function() {

    describe('PriceCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPriceCategory, MockAlterationPrice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPriceCategory = jasmine.createSpy('MockPriceCategory');
            MockAlterationPrice = jasmine.createSpy('MockAlterationPrice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PriceCategory': MockPriceCategory,
                'AlterationPrice': MockAlterationPrice
            };
            createController = function() {
                $injector.get('$controller')("PriceCategoryAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:priceCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
