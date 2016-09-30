'use strict';

angular.module('msapApp')
    .controller('LookupLanguageController', function ($scope, $state, LookupLanguage, LookupLanguageSearch) {

        $scope.lookupLanguages = [];
        $scope.loadAll = function() {
            LookupLanguage.query(function(result) {
               $scope.lookupLanguages = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LookupLanguageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.lookupLanguages = result;
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
            $scope.lookupLanguage = {
                code: null,
                name: null,
                id: null
            };
        };
    });
