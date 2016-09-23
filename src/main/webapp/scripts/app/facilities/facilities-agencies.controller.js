'use strict';

angular.module('msapApp')
    .controller('FacilitiesAgenciesController',
        ['$scope',
            function ($scope) {
                $scope.viewConfig = {presentation: 'list'};
            }
        ]);
