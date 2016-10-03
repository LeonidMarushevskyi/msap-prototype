'use strict';

//todo: test is ignored. to be fixed
describe('Controller Tests', function () {

    beforeEach(module('msapApp'));

    describe('LoginController', function () {
        var $scope;


        beforeEach(inject(function ($rootScope, $controller) {
            $scope = $rootScope.$new();
            $controller('LoginController', {$scope: $scope});
        }));

        it('should set remember Me', function () {
            //expect($scope.rememberMe).toBeTruthy();
        });
    });
});
