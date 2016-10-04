'use strict';

describe('Controller Tests', function() {

    describe('Review Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReview, MockProvider, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReview = jasmine.createSpy('MockReview');
            MockProvider = jasmine.createSpy('MockProvider');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Review': MockReview,
                'Provider': MockProvider,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ReviewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:reviewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
