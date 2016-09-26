'use strict';

describe('Controller Tests', function() {

    describe('LookupLicenseType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupLicenseType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupLicenseType = jasmine.createSpy('MockLookupLicenseType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupLicenseType': MockLookupLicenseType
            };
            createController = function() {
                $injector.get('$controller')("LookupLicenseTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupLicenseTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
