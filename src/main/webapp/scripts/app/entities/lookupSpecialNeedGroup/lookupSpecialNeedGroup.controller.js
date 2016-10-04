'use strict';

angular.module('msapApp')
    .controller('LookupSpecialNeedGroupController', function ($scope, $state, LookupSpecialNeedGroup, LookupSpecialNeedGroupSearch) {

        $scope.lookupSpecialNeedGroups = [];
        $scope.loadAll = function() {
            LookupSpecialNeedGroup.query(function(result) {
               $scope.lookupSpecialNeedGroups = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupSpecialNeedGroupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupSpecialNeedGroups = result;
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
            $scope.lookupSpecialNeedGroup = {
                name: null,
                id: null
            };
        };
    });
