'use strict';

describe('Controller Tests', function() {

    describe('LookupAgeGroups Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupAgeGroups;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupAgeGroups = jasmine.createSpy('MockLookupAgeGroups');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupAgeGroups': MockLookupAgeGroups
            };
            createController = function() {
                $injector.get('$controller')("LookupAgeGroupsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupAgeGroupsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
