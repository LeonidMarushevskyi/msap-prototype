'use strict';

angular.module('msapApp').controller('ProviderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Provider', 'LookupLicenseType', 'LookupProviderType', 'Place', 'LookupQualityRating', 'OpenSlot', 'Schedule', 'SupportedSpecialNeed', 'Review', 'Price', 'SubstantiatedAllegation', 'SupportedLanguage',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Provider, LookupLicenseType, LookupProviderType, Place, LookupQualityRating, OpenSlot, Schedule, SupportedSpecialNeed, Review, Price, SubstantiatedAllegation, SupportedLanguage) {

        $scope.provider = entity;
        $scope.licensetypes = LookupLicenseType.query({filter: 'provider-is-null'});
        $q.all([$scope.provider.$promise, $scope.licensetypes.$promise]).then(function() {
            if (!$scope.provider.licenseType || !$scope.provider.licenseType.id) {
                return $q.reject();
            }
            return LookupLicenseType.get({id : $scope.provider.licenseType.id}).$promise;
        }).then(function(licenseType) {
            $scope.licensetypes.push(licenseType);
        });
        $scope.providertypes = LookupProviderType.query({filter: 'provider-is-null'});
        $q.all([$scope.provider.$promise, $scope.providertypes.$promise]).then(function() {
            if (!$scope.provider.providerType || !$scope.provider.providerType.id) {
                return $q.reject();
            }
            return LookupProviderType.get({id : $scope.provider.providerType.id}).$promise;
        }).then(function(providerType) {
            $scope.providertypes.push(providerType);
        });
        $scope.addresss = Place.query({filter: 'provider-is-null'});
        $q.all([$scope.provider.$promise, $scope.addresss.$promise]).then(function() {
            if (!$scope.provider.address || !$scope.provider.address.id) {
                return $q.reject();
            }
            return Place.get({id : $scope.provider.address.id}).$promise;
        }).then(function(address) {
            $scope.addresss.push(address);
        });
        $scope.qualityratings = LookupQualityRating.query({filter: 'provider-is-null'});
        $q.all([$scope.provider.$promise, $scope.qualityratings.$promise]).then(function() {
            if (!$scope.provider.qualityRating || !$scope.provider.qualityRating.id) {
                return $q.reject();
            }
            return LookupQualityRating.get({id : $scope.provider.qualityRating.id}).$promise;
        }).then(function(qualityRating) {
            $scope.qualityratings.push(qualityRating);
        });
        $scope.openslots = OpenSlot.query();
        $scope.schedules = Schedule.query();
        $scope.supportedspecialneeds = SupportedSpecialNeed.query();
        $scope.reviews = Review.query();
        $scope.prices = Price.query();
        $scope.substantiatedallegations = SubstantiatedAllegation.query();
        $scope.supportedlanguages = SupportedLanguage.query();
        $scope.load = function(id) {
            Provider.get({id : id}, function(result) {
                $scope.provider = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('msapApp:providerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function () {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.provider.id != null) {
                Provider.update($scope.provider, onSaveSuccess, onSaveError);
            } else {
                Provider.save($scope.provider, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForLastVisit = {};

        $scope.datePickerForLastVisit.status = {
            opened: false
        };

        $scope.datePickerForLastVisitOpen = function() {
            $scope.datePickerForLastVisit.status.opened = true;
        };
}]);
