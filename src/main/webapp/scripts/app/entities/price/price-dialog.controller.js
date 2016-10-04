'use strict';

angular.module('msapApp').controller('PriceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Price', 'Provider', 'LookupAgeGroups',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Price, Provider, LookupAgeGroups) {

        $scope.price = entity;
        $scope.providers = Provider.query();
        $scope.agegroups = LookupAgeGroups.query({filter: 'price-is-null'});
        $q.all([$scope.price.$promise, $scope.agegroups.$promise]).then(function() {
            if (!$scope.price.ageGroup || !$scope.price.ageGroup.id) {
                return $q.reject();
            }
            return LookupAgeGroups.get({id : $scope.price.ageGroup.id}).$promise;
        }).then(function(ageGroup) {
            $scope.agegroups.push(ageGroup);
        });
        $scope.load = function(id) {
            Price.get({id : id}, function(result) {
                $scope.price = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:priceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.price.id != null) {
                Price.update($scope.price, onSaveSuccess, onSaveError);
            } else {
                Price.save($scope.price, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
