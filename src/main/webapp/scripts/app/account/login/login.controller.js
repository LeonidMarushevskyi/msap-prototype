'use strict';

angular.module('msapApp')
    .controller('LoginController', ['$scope', '$state', '$uibModal', 'GeocoderService', 'lookupAgeGroups',
    function ($scope, $state, $uibModal, GeocoderService, lookupAgeGroups) {
        $scope.openSignInModal = function() {
            $uibModal.open({
                templateUrl: 'scripts/app/account/login/modal/sign-in-dialog.html',
                controller: 'SignInModalCtrl',
                size: 'sign-in',
                windowClass: 'ch-general-modal'
            });
        };

        $scope.initTwitterTimeline = function () {
            $("#twitter-wjs").remove();

            !function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
        };

        $scope.initTwitterTimeline();

        $scope.ageGroupsConfig = {
            showList: false,
            label: "Select age(s) of children needing care"
        };

        $scope.lookupAgeGroups = lookupAgeGroups;

        $scope.getSelected = function(model) {
            return _.map(_.filter(model, {selected: true}), 'code');
        };

        $scope.updateDropDownLabel = function(model, config, defaultValue) {
            var selected = $scope.getSelected(model);
            if (selected.length > 0) {
                config.label = selected.length + ' Selected';
            } else {
                config.label = defaultValue;
            }
        };
        $scope.updateAgeGroupLabel = function() {
            $scope.updateDropDownLabel(lookupAgeGroups, $scope.ageGroupsConfig, $scope.ageGroupsConfig.label);
        };
        $scope.onAgeGroupSelect = function() {
            $scope.updateAgeGroupLabel();
        };

        $scope.onSelectAddress = function (addressFeature) {
            $state.go('ch-facilities',
                angular.merge($state.params,
                    {
                        test: 'testing'
                    }
                )
            )
        };

        $scope.addGeocoder = function () {
            if(!$scope.geocoder) {
                $scope.geocoder = GeocoderService.createGeocoder("geoaddress", $scope.onSelectAddress);
            }
        };
        $scope.addGeocoder();

    }]);
