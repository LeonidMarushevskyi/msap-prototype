'use strict';

describe('Controller Tests', function() {

    describe('SubstantiatedAllegation Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubstantiatedAllegation, MockProvider;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubstantiatedAllegation = jasmine.createSpy('MockSubstantiatedAllegation');
            MockProvider = jasmine.createSpy('MockProvider');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubstantiatedAllegation': MockSubstantiatedAllegation,
                'Provider': MockProvider
            };
            createController = function() {
                $injector.get('$controller')("SubstantiatedAllegationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:substantiatedAllegationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
