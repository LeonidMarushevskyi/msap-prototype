'use strict';

angular.module('msapApp')
    .controller('LoginController', ['$scope', '$state', '$uibModal', 'GeocoderService', 'lookupAgeGroups',
    function ($scope, $state, $uibModal, GeocoderService, lookupAgeGroups) {
        $scope.addressFeature;

        $scope.openSignInModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/login/modal/sign-in-dialog.html',
                controller: 'SignInModalCtrl',
                size: 'sign-in',
                windowClass: 'ch-general-modal'
            });
        };

        $scope.openRegisterModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/register/register.html',
                controller: 'RegisterController',
                size: 'register',
                windowClass: 'ch-general-modal'
            });
        };

        $scope.initTwitterTimeline = function () {
            $("#twitter-wjs").remove();

            !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
        };

        $scope.initTwitterTimeline();

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

        $scope.lookupAgeGroups = lookupAgeGroups;

        $scope.getSelected = function(modelName) {
            return _.filter($scope[modelName], {selected: true});
        };
        $scope.getSelectedCodes = function(modelName) {
            return _.map($scope.getSelected(modelName), 'code');
        };

        $scope.onFilterMenuItemClick = function(modelName, code) {
            $scope.updateSelectedCount(modelName);
        };
        $scope.updateSelectedCount = function(modelName) {
            $scope.filterMenuConfigs[modelName].selectedCount = $scope.getSelected(modelName).length;
        };

        $scope.onSelectAddress = function (addressFeature) {
            $scope.addressFeature = addressFeature;
        };

        $scope.doSearch = function () {
            $state.go('ch-facilities',
                angular.merge($state.params,
                    {
                        latitude: $scope.addressFeature ? $scope.addressFeature.latlng.lat : null,
                        longitude: $scope.addressFeature ? $scope.addressFeature.latlng.lng : null,
                        geoLabel: $scope.addressFeature ? $scope.addressFeature.feature.properties.label : null,
                        ageGroupCodes: $scope.getSelectedCodes('lookupAgeGroups')
                    }
                ));
        };

        $scope.addGeocoder = function () {
            if(!$scope.geocoder) {
                $scope.geocoder = GeocoderService.createGeocoder("geoaddress", $scope.onSelectAddress);
            }
        };
        $scope.addGeocoder();

        $scope.openPrivacyPolicyModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/login/modal/privacy-policy-dialog.html',
                controller: 'PrivacyPolicyModalCtrl',
                size: 'privacy-policy',
                windowClass: 'ch-general-modal'
            });
        };

    }]);
