'use strict';

describe('Controller Tests', function() {

    describe('Schedule Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSchedule, MockProvider, MockLookupDayOfWeek;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSchedule = jasmine.createSpy('MockSchedule');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupDayOfWeek = jasmine.createSpy('MockLookupDayOfWeek');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Schedule': MockSchedule,
                'Provider': MockProvider,
                'LookupDayOfWeek': MockLookupDayOfWeek
            };
            createController = function() {
                $injector.get('$controller')("ScheduleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:scheduleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
