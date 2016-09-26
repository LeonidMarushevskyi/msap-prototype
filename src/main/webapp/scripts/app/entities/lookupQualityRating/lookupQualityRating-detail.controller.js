'use strict';

angular.module('msapApp')
    .controller('LookupQualityRatingDetailController', function ($scope, $rootScope, $stateParams, entity, LookupQualityRating) {
        $scope.lookupQualityRating = entity;
        $scope.load = function (id) {
            LookupQualityRating.get({id: id}, function(result) {
                $scope.lookupQualityRating = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupQualityRatingUpdate', function(event, result) {
            $scope.lookupQualityRating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
