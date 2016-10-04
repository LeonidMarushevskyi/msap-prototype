'use strict';

angular.module('msapApp')
    .controller('AttachmentDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Attachment) {
        $scope.attachment = entity;
        $scope.load = function (id) {
            Attachment.get({id: id}, function(result) {
                $scope.attachment = result;
            });
        };
        var unsubscribe = $rootScope.$on('msapApp:attachmentUpdate', function(event, result) {
            $scope.attachment = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
