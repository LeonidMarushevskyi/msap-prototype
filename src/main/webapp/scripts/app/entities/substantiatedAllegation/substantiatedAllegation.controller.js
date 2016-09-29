'use strict';

angular.module('msapApp')
    .controller('SubstantiatedAllegationController', function ($scope, $state, SubstantiatedAllegation, SubstantiatedAllegationSearch) {

        $scope.substantiatedAllegations = [];
        $scope.loadAll = function() {
            SubstantiatedAllegation.query(function(result) {
               $scope.substantiatedAllegations = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            SubstantiatedAllegationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.substantiatedAllegations = result;
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
            $scope.substantiatedAllegation = {
                description: null,
                severity: null,
                allegationDate: null,
                id: null
            };
        };
    });
