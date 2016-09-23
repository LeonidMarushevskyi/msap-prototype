'use strict';

angular.module('msapApp')
    .controller('LookupCountyDetailController', function ($scope, $rootScope, $stateParams, entity, LookupCounty) {
        $scope.lookupCounty = entity;
        $scope.load = function (id) {
            LookupCounty.get({id: id}, function(result) {
                $scope.lookupCounty = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupCountyUpdate', function(event, result) {
            $scope.lookupCounty = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
