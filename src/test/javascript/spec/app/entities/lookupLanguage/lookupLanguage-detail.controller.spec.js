'use strict';

describe('Controller Tests', function() {

    describe('LookupLanguage Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupLanguage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupLanguage = jasmine.createSpy('MockLookupLanguage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupLanguage': MockLookupLanguage
            };
            createController = function() {
                $injector.get('$controller')("LookupLanguageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupLanguageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
