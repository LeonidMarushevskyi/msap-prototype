'use strict';

describe('Controller Tests', function() {

    describe('OpenSlot Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOpenSlot, MockProvider, MockLookupAgeGroups;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOpenSlot = jasmine.createSpy('MockOpenSlot');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupAgeGroups = jasmine.createSpy('MockLookupAgeGroups');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'OpenSlot': MockOpenSlot,
                'Provider': MockProvider,
                'LookupAgeGroups': MockLookupAgeGroups
            };
            createController = function() {
                $injector.get('$controller')("OpenSlotDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:openSlotUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
