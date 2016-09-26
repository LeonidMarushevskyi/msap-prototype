'use strict';

describe('Controller Tests', function() {

    describe('LookupProviderType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupProviderType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupProviderType = jasmine.createSpy('MockLookupProviderType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupProviderType': MockLookupProviderType
            };
            createController = function() {
                $injector.get('$controller')("LookupProviderTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupProviderTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
