'use strict';

angular.module('msapApp')
    .controller('SupportedSpecialNeedController', function ($scope, $state, SupportedSpecialNeed, SupportedSpecialNeedSearch) {

        $scope.supportedSpecialNeeds = [];
        $scope.loadAll = function() {
            SupportedSpecialNeed.query(function(result) {
               $scope.supportedSpecialNeeds = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            SupportedSpecialNeedSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.supportedSpecialNeeds = result;
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
            $scope.supportedSpecialNeed = {
                id: null
            };
        };
    });
