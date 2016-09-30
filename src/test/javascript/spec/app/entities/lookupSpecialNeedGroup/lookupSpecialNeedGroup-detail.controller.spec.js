'use strict';

describe('Controller Tests', function() {

    describe('LookupSpecialNeedGroup Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLookupSpecialNeedGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLookupSpecialNeedGroup = jasmine.createSpy('MockLookupSpecialNeedGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LookupSpecialNeedGroup': MockLookupSpecialNeedGroup
            };
            createController = function() {
                $injector.get('$controller')("LookupSpecialNeedGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'msapApp:lookupSpecialNeedGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
