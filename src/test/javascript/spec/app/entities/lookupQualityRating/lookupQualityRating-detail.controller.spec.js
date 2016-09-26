'use strict';

describe('Controller Tests', function() {

    describe('LookupQualityRating Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupQualityRating;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupQualityRating = jasmine.createSpy('MockLookupQualityRating');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupQualityRating': MockLookupQualityRating
            };
            createController = function() {
                $injector.get('$controller')("LookupQualityRatingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupQualityRatingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
