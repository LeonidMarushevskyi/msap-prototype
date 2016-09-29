'use strict';

angular.module('msapApp')
    .controller('LookupLanguageDetailController', function ($scope, $rootScope, $stateParams, entity, LookupLanguage) {
        $scope.lookupLanguage = entity;
        $scope.load = function (id) {
            LookupLanguage.get({id: id}, function(result) {
                $scope.lookupLanguage = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:lookupLanguageUpdate', function(event, result) {
            $scope.lookupLanguage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
