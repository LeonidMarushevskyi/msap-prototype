'use strict';

describe('Controller Tests', function() {

    describe('SupportedSpecialNeed Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSupportedSpecialNeed, MockProvider, MockLookupSpecialNeedType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSupportedSpecialNeed = jasmine.createSpy('MockSupportedSpecialNeed');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupSpecialNeedType = jasmine.createSpy('MockLookupSpecialNeedType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SupportedSpecialNeed': MockSupportedSpecialNeed,
                'Provider': MockProvider,
                'LookupSpecialNeedType': MockLookupSpecialNeedType
            };
            createController = function() {
                $injector.get('$controller')("SupportedSpecialNeedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:supportedSpecialNeedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
