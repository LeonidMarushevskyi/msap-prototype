'use strict';

angular.module('msapApp')
    .controller('LookupProviderTypeController', function ($scope, $state, LookupProviderType, LookupProviderTypeSearch) {

        $scope.lookupProviderTypes = [];
        $scope.loadAll = function() {
            LookupProviderType.query(function(result) {
               $scope.lookupProviderTypes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupProviderTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupProviderTypes = result;
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
            $scope.lookupProviderType = {
                code: null,
                name: null,
                id: null
            };
        };
    });
