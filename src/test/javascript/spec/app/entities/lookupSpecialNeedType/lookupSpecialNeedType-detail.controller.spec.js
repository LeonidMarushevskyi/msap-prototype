'use strict';

describe('Controller Tests', function() {

    describe('LookupSpecialNeedType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupSpecialNeedType, MockProvider;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupSpecialNeedType = jasmine.createSpy('MockLookupSpecialNeedType');
            MockProvider = jasmine.createSpy('MockProvider');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupSpecialNeedType': MockLookupSpecialNeedType,
                'Provider': MockProvider
            };
            createController = function() {
                $injector.get('$controller')("LookupSpecialNeedTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupSpecialNeedTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
