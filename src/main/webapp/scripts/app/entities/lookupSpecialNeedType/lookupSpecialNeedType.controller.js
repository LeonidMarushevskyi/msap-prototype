'use strict';

angular.module('msapApp')
    .controller('LookupSpecialNeedTypeController', function ($scope, $state, LookupSpecialNeedType, LookupSpecialNeedTypeSearch) {

        $scope.lookupSpecialNeedTypes = [];
        $scope.loadAll = function() {
            LookupSpecialNeedType.query(function(result) {
               $scope.lookupSpecialNeedTypes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupSpecialNeedTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupSpecialNeedTypes = result;
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
            $scope.lookupSpecialNeedType = {
                code: null,
                name: null,
                id: null
            };
        };
    });
