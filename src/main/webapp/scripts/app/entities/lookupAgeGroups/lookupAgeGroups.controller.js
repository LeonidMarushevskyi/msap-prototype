'use strict';

angular.module('msapApp')
    .controller('LookupAgeGroupsController', function ($scope, $state, LookupAgeGroups, LookupAgeGroupsSearch) {

        $scope.lookupAgeGroupss = [];
        $scope.loadAll = function() {
            LookupAgeGroups.query(function(result) {
               $scope.lookupAgeGroupss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupAgeGroupsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupAgeGroupss = result;
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
            $scope.lookupAgeGroups = {
                code: null,
                name: null,
                id: null
            };
        };
    });
