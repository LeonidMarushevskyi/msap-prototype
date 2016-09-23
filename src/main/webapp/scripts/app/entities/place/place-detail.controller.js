'use strict';

angular.module('msapApp')
    .controller('PlaceDetailController', function ($scope, $rootScope, $stateParams, entity, Place) {
        $scope.place = entity;
        $scope.load = function (id) {
            Place.get({id: id}, function(result) {
                $scope.place = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:placeUpdate', function(event, result) {
            $scope.place = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
