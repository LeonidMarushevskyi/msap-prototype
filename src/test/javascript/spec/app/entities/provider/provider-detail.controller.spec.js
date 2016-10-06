'use strict';

describe('Controller Tests', function() {

    describe('Provider Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProvider, MockLookupLicenseType, MockLookupProviderType, MockPlace, MockLookupQualityRating, MockOpenSlot, MockSchedule, MockSupportedSpecialNeed, MockReview, MockPrice, MockSubstantiatedAllegation, MockSupportedLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupLicenseType = jasmine.createSpy('MockLookupLicenseType');
            MockLookupProviderType = jasmine.createSpy('MockLookupProviderType');
            MockPlace = jasmine.createSpy('MockPlace');
            MockLookupQualityRating = jasmine.createSpy('MockLookupQualityRating');
            MockOpenSlot = jasmine.createSpy('MockOpenSlot');
            MockSchedule = jasmine.createSpy('MockSchedule');
            MockSupportedSpecialNeed = jasmine.createSpy('MockSupportedSpecialNeed');
            MockReview = jasmine.createSpy('MockReview');
            MockPrice = jasmine.createSpy('MockPrice');
            MockSubstantiatedAllegation = jasmine.createSpy('MockSubstantiatedAllegation');
            MockSupportedLanguage = jasmine.createSpy('MockSupportedLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Provider': MockProvider,
                'LookupLicenseType': MockLookupLicenseType,
                'LookupProviderType': MockLookupProviderType,
                'Place': MockPlace,
                'LookupQualityRating': MockLookupQualityRating,
                'OpenSlot': MockOpenSlot,
                'Schedule': MockSchedule,
                'SupportedSpecialNeed': MockSupportedSpecialNeed,
                'Review': MockReview,
                'Price': MockPrice,
                'SubstantiatedAllegation': MockSubstantiatedAllegation,
                'SupportedLanguage': MockSupportedLanguage
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
