'use strict';

angular.module('msapApp')
    .controller('OpenSlotController', function ($scope, $state, OpenSlot, OpenSlotSearch) {

        $scope.openSlots = [];
        $scope.loadAll = function() {
            OpenSlot.query(function(result) {
               $scope.openSlots = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            OpenSlotSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.openSlots = result;
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
            $scope.openSlot = {
                openSlots: null,
                id: null
            };
        };
    });
