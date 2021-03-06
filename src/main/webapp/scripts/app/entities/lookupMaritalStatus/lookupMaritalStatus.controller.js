'use strict';

angular.module('msapApp')
    .controller('LookupMaritalStatusController', function ($scope, $state, LookupMaritalStatus, LookupMaritalStatusSearch) {

        $scope.lookupMaritalStatuss = [];
        $scope.loadAll = function() {
            LookupMaritalStatus.query(function(result) {
               $scope.lookupMaritalStatuss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupMaritalStatusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupMaritalStatuss = result;
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
            $scope.lookupMaritalStatus = {
                code: null,
                maritalStatusName: null,
                id: null
            };
        };
    });
