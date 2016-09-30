'use strict';

describe('Controller Tests', function() {

    describe('Provider Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProvider, MockLookupLicenseType, MockLookupProviderType, MockPlace, MockLookupQualityRating, MockOpenSlot, MockSchedule, MockLookupSpecialNeedType, MockReview, MockPrice, MockSubstantiatedAllegation, MockLookupLanguage;
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
            MockLookupSpecialNeedType = jasmine.createSpy('MockLookupSpecialNeedType');
            MockReview = jasmine.createSpy('MockReview');
            MockPrice = jasmine.createSpy('MockPrice');
            MockSubstantiatedAllegation = jasmine.createSpy('MockSubstantiatedAllegation');
            MockLookupLanguage = jasmine.createSpy('MockLookupLanguage');
            

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
                'LookupSpecialNeedType': MockLookupSpecialNeedType,
                'Review': MockReview,
                'Price': MockPrice,
                'SubstantiatedAllegation': MockSubstantiatedAllegation,
                'LookupLanguage': MockLookupLanguage
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
