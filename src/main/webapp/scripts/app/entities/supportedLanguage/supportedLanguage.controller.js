'use strict';

angular.module('msapApp')
    .controller('SupportedLanguageController', function ($scope, $state, SupportedLanguage, SupportedLanguageSearch) {

        $scope.supportedLanguages = [];
        $scope.loadAll = function() {
            SupportedLanguage.query(function(result) {
               $scope.supportedLanguages = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            SupportedLanguageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.supportedLanguages = result;
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
            $scope.supportedLanguage = {
                id: null
            };
        };
    });
