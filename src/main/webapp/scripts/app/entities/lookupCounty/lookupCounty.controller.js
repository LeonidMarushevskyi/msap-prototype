'use strict';

angular.module('msapApp')
    .controller('LookupCountyController', function ($scope, $state, LookupCounty, LookupCountySearch) {

        $scope.lookupCountys = [];
        $scope.loadAll = function() {
            LookupCounty.query(function(result) {
               $scope.lookupCountys = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupCountySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupCountys = result;
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
            $scope.lookupCounty = {
                countyName: null,
                countyCode: null,
                id: null
            };
        };
    });
