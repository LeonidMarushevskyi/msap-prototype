'use strict';

describe('Controller Tests', function() {

    describe('SupportedLanguage Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSupportedLanguage, MockProvider, MockLookupLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSupportedLanguage = jasmine.createSpy('MockSupportedLanguage');
            MockProvider = jasmine.createSpy('MockProvider');
            MockLookupLanguage = jasmine.createSpy('MockLookupLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SupportedLanguage': MockSupportedLanguage,
                'Provider': MockProvider,
                'LookupLanguage': MockLookupLanguage
            };
            createController = function() {
                $injector.get('$controller')("SupportedLanguageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:supportedLanguageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
