'use strict';

angular.module('msapApp')
    .controller('ProviderController', function ($scope, $state, Provider, ProviderSearch) {

        $scope.providers = [];
        $scope.loadAll = function() {
            Provider.query(function(result) {
               $scope.providers = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ProviderSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.providers = result;
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
            $scope.provider = {
                providerCapacity: null,
                providerName: null,
                phoneNumber: null,
                isOpenOvernight: null,
                numberOfComplains: null,
                numberOfVisits: null,
                lastVisit: null,
                description: null,
                isFullDay: null,
                isAfterSchool: null,
                isBeforeSchool: null,
                isWeekendCare: null,
                id: null
            };
        };
    });
