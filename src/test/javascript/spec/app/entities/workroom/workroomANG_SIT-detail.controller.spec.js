'use strict';

describe('Controller Tests', function() {

    describe('Workroom Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkroom, MockWorkroomMetric, MockSitUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkroom = jasmine.createSpy('MockWorkroom');
            MockWorkroomMetric = jasmine.createSpy('MockWorkroomMetric');
            MockSitUser = jasmine.createSpy('MockSitUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Workroom': MockWorkroom,
                'WorkroomMetric': MockWorkroomMetric,
                'SitUser': MockSitUser
            };
            createController = function() {
                $injector.get('$controller')("WorkroomAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:workroomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
