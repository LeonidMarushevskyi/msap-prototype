'use strict';

angular.module('msapApp')
    .controller('DefaultAddressModalCtrl',
        ['$scope', '$state', '$log', '$uibModalInstance', 'Auth', 'userProfile', 'GeocoderService', 'AddressUtils', 'Place', 'LookupAgeGroups',
        function ($scope, $state, $log, $uibModalInstance, Auth, userProfile, GeocoderService, AddressUtils, Place, LookupAgeGroups) {
            $scope.userProfile = userProfile;
            $scope.updateProfile = function(addressFeature) {
                AddressUtils.addAddressToAccount(addressFeature, $scope.userProfile).then(
                    function() {
                        $scope.saveOrUpdateAccount($scope.userProfile);
                    }
                );
            };

            $scope.lookupAgeGroups = [];
            LookupAgeGroups.query(function(result) {
                $scope.lookupAgeGroups = result;
            });

            $scope.filterMenuConfigs = {
                lookupAgeGroups: {
                    showList: false,
                    selectedCount: 0
                }
            };

            $scope.toggleFilterMenu = function (modelName, status) {
                if (_.isUndefined(status)) {
                    $scope.filterMenuConfigs[modelName].showList = !$scope.filterMenuConfigs[modelName].showList;
                } else {
                    $scope.filterMenuConfigs[modelName].showList = status;
                }
            };

            $scope.saveOrUpdateAccount = function(profile) {
                if (profile.place.id) {
                    Place.update(profile.place, function (place) {
                        $scope.updateAccount(profile, place);
                    });
                } else {
                    if (!profile.place.streetName) {
                        profile.place.streetName = '';
                    }
                    Place.save(profile.place, function (place) {
                        $scope.updateAccount(profile, place);
                    });
                }
            };
            $scope.updateAccount = function(profile, place) {
                profile.place = place;
                Auth.updateAccount(profile);
            };

            $scope.onApplyAddress = function() {
                var isStoredInProfile;
                if ($scope.saveAddressToProfile && $scope.addressFeature && $scope.userProfile && $scope.userProfile.authorities) {
                    $scope.updateProfile($scope.addressFeature);
                    isStoredInProfile = true;
                }
                $scope.close({
                    addressFeature: $scope.addressFeature,
                    ageGroups: $scope.getSelectedCodes('lookupAgeGroups'),
                    isStoredInProfile: isStoredInProfile
                });
            };

            $scope.close = function (addressFeature) {
                $uibModalInstance.close(addressFeature);
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };

            $scope.keepAddress = function(addressFeature) {
                $scope.addressFeature = addressFeature;
            };

            $uibModalInstance.rendered.then(
                function() {
                    if(!$scope.geolocator) {
                        $scope.geolocator = GeocoderService.createGeocoder("geolocator", $scope.keepAddress)
                    }
                },
                function(reason) {
                    $log.warn('Cannot render modal ', reason)
                }
            );

            $scope.getSelected = function(modelName) {
                return _.filter($scope[modelName], {selected: true});
            };
            $scope.getSelectedCodes = function(modelName) {
                return _.map($scope.getSelected(modelName), 'code');
            };
        }]
    );
