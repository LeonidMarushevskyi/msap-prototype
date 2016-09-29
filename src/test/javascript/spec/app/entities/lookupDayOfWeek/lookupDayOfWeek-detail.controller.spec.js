'use strict';

describe('Controller Tests', function() {

    describe('LookupDayOfWeek Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupDayOfWeek;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupDayOfWeek = jasmine.createSpy('MockLookupDayOfWeek');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupDayOfWeek': MockLookupDayOfWeek
            };
            createController = function() {
                $injector.get('$controller')("LookupDayOfWeekDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupDayOfWeekUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
