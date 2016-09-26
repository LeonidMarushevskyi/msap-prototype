'use strict';

angular.module('msapApp')
    .controller('LookupLicenseTypeController', function ($scope, $state, LookupLicenseType, LookupLicenseTypeSearch) {

        $scope.lookupLicenseTypes = [];
        $scope.loadAll = function() {
            LookupLicenseType.query(function(result) {
               $scope.lookupLicenseTypes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupLicenseTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupLicenseTypes = result;
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
            $scope.lookupLicenseType = {
                name: null,
                id: null
            };
        };
    });
