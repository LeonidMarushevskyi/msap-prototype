'use strict';

describe('Controller Tests', function() {

    describe('Provider Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProvider, MockLookupLicenseType, MockLookupQualityRating, MockPlace, MockLookupProviderType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupLicenseType = jasmine.createSpy('MockLookupLicenseType');
            MockLookupQualityRating = jasmine.createSpy('MockLookupQualityRating');
            MockPlace = jasmine.createSpy('MockPlace');
            MockLookupProviderType = jasmine.createSpy('MockLookupProviderType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Provider': MockProvider,
                'LookupLicenseType': MockLookupLicenseType,
                'LookupQualityRating': MockLookupQualityRating,
                'Place': MockPlace,
                'LookupProviderType': MockLookupProviderType
            };
            createController = function() {
                $injector.get('$controller')("ProviderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:providerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
