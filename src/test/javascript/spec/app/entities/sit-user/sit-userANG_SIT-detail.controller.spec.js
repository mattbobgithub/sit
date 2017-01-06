'use strict';

describe('Controller Tests', function() {

    describe('SitUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSitUser, MockCompany, MockStore, MockWorkroom, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSitUser = jasmine.createSpy('MockSitUser');
            MockCompany = jasmine.createSpy('MockCompany');
            MockStore = jasmine.createSpy('MockStore');
            MockWorkroom = jasmine.createSpy('MockWorkroom');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SitUser': MockSitUser,
                'Company': MockCompany,
                'Store': MockStore,
                'Workroom': MockWorkroom,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("SitUserAngSitDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'sitApp:sitUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
