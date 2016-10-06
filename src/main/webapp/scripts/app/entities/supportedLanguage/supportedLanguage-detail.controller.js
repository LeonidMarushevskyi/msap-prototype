'use strict';

angular.module('msapApp')
    .controller('SupportedLanguageDetailController', function ($scope, $rootScope, $stateParams, entity, SupportedLanguage) {
        $scope.supportedLanguage = entity;
        $scope.load = function (id) {
            SupportedLanguage.get({id: id}, function(result) {
                $scope.supportedLanguage = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:supportedLanguageUpdate', function(event, result) {
            $scope.supportedLanguage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
