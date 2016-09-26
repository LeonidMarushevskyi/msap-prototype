'use strict';

angular.module('msapApp')
    .controller('LookupQualityRatingController', function ($scope, $state, LookupQualityRating, LookupQualityRatingSearch) {

        $scope.lookupQualityRatings = [];
        $scope.loadAll = function() {
            LookupQualityRating.query(function(result) {
               $scope.lookupQualityRatings = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupQualityRatingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupQualityRatings = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lookupQualityRating = {
                code: null,
                name: null,
                id: null
            };
        };
    });
