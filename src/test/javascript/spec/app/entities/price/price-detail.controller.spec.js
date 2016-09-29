'use strict';

describe('Controller Tests', function() {

    describe('Price Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPrice, MockProvider, MockLookupAgeGroups;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPrice = jasmine.createSpy('MockPrice');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupAgeGroups = jasmine.createSpy('MockLookupAgeGroups');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Price': MockPrice,
                'Provider': MockProvider,
                'LookupAgeGroups': MockLookupAgeGroups
            };
            createController = function() {
                $injector.get('$controller')("PriceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:priceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
