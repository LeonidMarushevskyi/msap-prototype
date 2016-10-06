'use strict';

angular.module('msapApp')
    .controller('FacilitiesController',
    ['$scope', '$state', '$log', '$q', 'searchParams',
        'leafletData', 'QualityRatingStars', 'ProviderAgenciesService',
        'GeocoderService', 'chLayoutConfigFactory', '$uibModal', 'Principal', 'AppPropertiesService', 'AddressUtils',
        'lookupAgeGroups', 'lookupQualityRating', 'lookupProviderType', 'lookupWorkingHours',
        'lookupSpecialNeedGroup', 'lookupSpecialNeedType', 'lookupLicenseType', 'lookupLanguage',
    function ($scope, $state, $log, $q, searchParams,
              leafletData, QualityRatingStars, ProviderAgenciesService,
              GeocoderService, chLayoutConfigFactory, $uibModal, Principal, AppPropertiesService, AddressUtils,
              lookupAgeGroups, lookupQualityRating, lookupProviderType, lookupWorkingHours,
              lookupSpecialNeedGroup, lookupSpecialNeedType, lookupLicenseType, lookupLanguage) {

        $scope.searchParams = searchParams;
        $scope.lookupAgeGroups = lookupAgeGroups;
        $scope.lookupProviderType = lookupProviderType;
        $scope.lookupQualityRating = lookupQualityRating;
        $scope.lookupWorkingHours = lookupWorkingHours;
        $scope.lookupSpecialNeedGroup = lookupSpecialNeedGroup;
        $scope.lookupSpecialNeedType = lookupSpecialNeedType;
        $scope.lookupLicenseType = lookupLicenseType;
        $scope.lookupLanguage = lookupLanguage;
        //
        $scope.MENU_CONFIG = {
            showList: false,
            selectedCount: 0
        };
        //
        $scope.filterMenuConfigs = {
            lookupAgeGroups: _.cloneDeep($scope.MENU_CONFIG),
            lookupProviderType: _.cloneDeep($scope.MENU_CONFIG),
            lookupQualityRating: _.cloneDeep($scope.MENU_CONFIG),
            lookupWorkingHours: _.cloneDeep($scope.MENU_CONFIG),
            lookupSpecialNeedGroup: _.cloneDeep($scope.MENU_CONFIG),
            lookupSpecialNeedType: _.cloneDeep($scope.MENU_CONFIG),
            lookupLicenseType: _.cloneDeep($scope.MENU_CONFIG),
            lookupLanguage: _.cloneDeep($scope.MENU_CONFIG)
        };

        var agenciesDataSource;
        var agenciesViewIndex;
        var agenciesViewPage = 10;
        var windowWidth = $(window).width();

        $scope.returnMapHeight = function() {
          var heightMapDesktop = "height: calc(100vh - 19rem)";
          var heightMapMobile = "height: calc(100vh - 25rem)";
            if (windowWidth > 640) {
                return heightMapDesktop;
            } else {
                return heightMapMobile;
            }
        };

        $scope.agenciesLength = 0;

        $scope.DEFAULT_MARKER_MESSAGE = 'You are here';
        $scope.DEFAULT_ZOOM = 13;

        $scope.viewContainsCaBounds = false;
        $scope.caBounds = new L.LatLngBounds(new L.LatLng(32.53, -124.43), new L.LatLng(42, -114.13));

        $scope.defaults = {
            zoomControlPosition: 'bottomright',
            scrollWheelZoom: true,
            maxZoom: 18
        };
        $scope.layers = {
            baselayers: {
                osm: {
                    name: 'Map',
                    type: 'xyz',
                    url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                }
            },
            overlays: {
                agencies: {
                    name: "Agencies",
                    type: "markercluster",
                    visible: true
                },
                place: {
                    name: "Place",
                    type: "group",
                    visible: true
                }
            }
        };

        $scope.viewConfig = {presentation: 'list'};
        $scope.center = {lat: 0, lng: 0, zoom: $scope.DEFAULT_ZOOM};

        $scope.getIconUrl = function(id) {
            return $('#' + 'icon_pin_' + id)[0].src;
        };

        $scope.getHomeLocation = function (latLng, message) {
            if (message) {
                if (!$scope.geocoder._input) {
                    var watcherUnregister = $scope.$watch('geocoder._input', function(newValue) {
                        if (newValue) {
                            $scope.geocoder._input.value = message;
                            watcherUnregister();
                        }
                    });
                } else {
                    $scope.geocoder._input.value = message;
                }
            }
            if (!message) {
                message = $scope.DEFAULT_MARKER_MESSAGE;
            }
            return {
                layer: 'place',
                lat: latLng.lat,
                lng: latLng.lng,
                //focus: true,
                message: message,
                icon: {
                    iconUrl: $scope.getIconUrl("home"),
                    iconAnchor: [46, 46]
                }
            };
        };

        $scope.currentLocation = $scope.getHomeLocation($scope.center);

        $scope.searchText = '';

        $scope.showMapView = 'ch-show-map-view';
        $scope.showLinkMapView = 'ch-mobile-mailbox__nav-tab__link_active';

        $scope.mobileFilterState = 'mobile-filter-for-map-view';
        $scope.changeMapView = function() {
            $scope.showMapView = 'ch-show-map-view';
            $scope.showLinkMapView = 'ch-mobile-mailbox__nav-tab__link_active';
            $scope.showListView = '';
            $scope.showLinkListView= '';
            $scope.showLinkFilterView = '';
            $scope.mobileFilterState = 'mobile-filter-for-map-view';
        };

        $scope.changeListView = function() {
            $scope.showMapView = '';
            $scope.showLinkMapView = '';
            $scope.showListView = 'ch-show-list-view';
            $scope.showLinkListView = 'ch-mobile-mailbox__nav-tab__link_active';
            $scope.showLinkFilterView = '';
            $scope.mobileFilterState = 'mobile-filter-for-list-view';
        };

        $scope.changeFilterView = function() {
            $scope.showListView = '';
            $scope.showLinkListView = '';
            $scope.showMapView = '';
            $scope.showLinkMapView = '';
            $scope.showLinkFilterView = 'ch-mobile-mailbox__nav-tab__link_active';
            $scope.mobileFilterState = 'mobile-filter-for-filter-view';
        };

        $scope.applyInfiniteScroll = function () {
            $('.ch-aside-facilities').bind('scroll', function(){
                if($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight / 1.1){
                    $scope.$apply($scope.moreAgencies);
                }
            });
        };

        $scope.createLocations = function() {
            var locations = {};
            _.each(agenciesDataSource, function (agency) {
                agency.formattedAddress = AddressUtils.formatAddress(agency.address);
                agency.totalSpots = _.sumBy(agency.openSlots, 'openSlots');
                agency.distanceValue = GeocoderService.distance(
                    {
                        latitude: agency.address.latitude,
                        longitude: agency.address.longitude
                    },
                    {
                        latitude: $scope.currentLocation.lat,
                        longitude: $scope.currentLocation.lng
                    }
                );
                agency.distance = agency.distanceValue.toFixed(1);
                locations['fn' + agency.facility_number + '_' + agency.distance.replace('.', '_')] = {
                    layer: 'agencies',
                    lat: agency.address.latitude,
                    lng: agency.address.longitude,
                    message: '<div ng-include src="\'scripts/app/facilities/location-popup.html\'"></div>',
                    getMessageScope: function () {
                        var scope = $scope.$new();
                        scope.agency = agency;
                        scope.viewConfig = {presentation: 'popup'};
                        return scope;
                    },
                    icon: {
                        iconUrl: $scope.defineIcon(agency),
                        iconAnchor: [13, 13]
                    }
                };
            });
            agenciesDataSource.sort(function (a, b) {
                return a.distanceValue - b.distanceValue;
            });
            if ($scope.currentLocation) {
                locations.current = $scope.currentLocation;
            }

            $scope.agencies = agenciesDataSource.slice(0, agenciesViewPage);
            agenciesViewIndex = agenciesViewPage;
            $scope.applyInfiniteScroll();
            $scope.agenciesLength = agenciesDataSource.length;
            return locations;
        };

        $scope.moreAgencies = function () {
            if($scope.agencies && agenciesViewIndex < agenciesDataSource.length) {
                $scope.agencies = $scope.agencies.concat(agenciesDataSource.slice(agenciesViewIndex, agenciesViewIndex += agenciesViewPage));
            }
        };

        $scope.updateLocations = function() {
            $scope.locations = $scope.createLocations();
        };

        $scope.defineIcon = function(agency) {
            var iconColor = 'grey';
            if (agency.totalSpots > 0) {
                iconColor = 'green';
            }
            var imgId = iconColor + '_'
                + _.find(QualityRatingStars, {code: agency.qualityRating.code}).stars;
            return $scope.getIconUrl(imgId);
        };

        $scope.findLocationByAddress = function(address) {
            $log.debug('findLocationByAddress', address);
            $scope.onSelectAddress($scope.addressFeature);
        };

        $scope.findAgenciesByTextQuery = function(event) {
            var keyCode = event.which || event.keyCode;
            if (keyCode === 13) {
                $scope.searchText = $scope.text;
            } else {
                return;
            }
            $scope.invalidate();
        };

        $scope.findAgenciesWithinBox = function(bounds) {
            $scope.text = $scope.searchText;
            if ($scope.center.lat === 0 && $scope.center.lng === 0) {
                return;
            }

            var northEast = bounds._northEast;
            var southWest = bounds._southWest;
            var request = {
                bounds: {
                    northwest: {
                        latitude: northEast.lat,
                        longitude: southWest.lng
                    },
                    southeast: {
                        latitude: southWest.lat,
                        longitude: northEast.lng
                    }
                },
                text: $scope.searchText,
                ageGroups: $scope.getSelected('lookupAgeGroups'),
                providerTypeCodes: $scope.getSelectedCodes('lookupProviderType'),
                qualityRatingCodes: $scope.getSelectedCodes('lookupQualityRating'),
                isBeforeSchool: $scope.isSelected('lookupWorkingHours', 1),
                isAfterSchool: $scope.isSelected('lookupWorkingHours', 2),
                isFullDay: $scope.isSelected('lookupWorkingHours', 3),
                isWeekendCare: $scope.isSelected('lookupWorkingHours', 4),
                isOpenOvernight: $scope.isSelected('lookupWorkingHours', 5),
                licenseTypeCodes: $scope.getSelectedCodes('lookupLicenseType'),
                specialNeedCodes: $scope.getSelectedCodes('lookupSpecialNeedType'),
                supportedLanguageCodes: $scope.getSelectedCodes('lookupLanguage')
            };
            //$log.debug('request', request);

            ProviderAgenciesService.findAgenciesByFilter(request).then(
                function(agencies) {
                    agenciesDataSource = agencies;
                    $scope.updateLocations();
                },
                function(reason) {
                    $log.error('Failed to get agencies from findAgenciesByFilter', reason);
                }
            );
        };

        $scope.$on("leafletDirectiveMap.viewreset", function(event) {
            $log.debug(event.name);
            $scope.invalidate($scope.isDirty);
        });

        $scope.$on("leafletDirectiveMap.dragend", function(event) {
            $log.debug(event.name);
            $scope.invalidate($scope.isDirty);
        });

        $scope.$on("leafletDirectiveMap.resize", function(event) {
            $log.debug(event.name);
            $scope.invalidate($scope.isDirty);
        });

        $scope.isDirty = function(bounds) {
            if (bounds.contains($scope.caBounds)) {
                if ($scope.viewContainsCaBounds) {
                    return false;
                } else {
                    $scope.viewContainsCaBounds = true;
                }
            } else {
                $scope.viewContainsCaBounds = false;
            }
            return true;
        };

        $scope.invalidate = function(isDirty, zoom) {
            leafletData.getMap().then(function (map) {
                var bounds = map.getBounds();
                if (isDirty && !isDirty(bounds)) {
                    return;
                }
                if (zoom) {
                    map.setZoom($scope.DEFAULT_ZOOM);
                }
                $scope.findAgenciesWithinBox(bounds);
            }, function(reason) {
                $log.error("Cannot get map instance. ", reason);
            });
        };

        $scope.onSelectAddress = function (addressFeature) {
            if (_.isNil(addressFeature)) {
                return;
            }
            $log.debug(addressFeature);
            var latLng = addressFeature.latlng;
            $scope.center.lat = latLng.lat;
            $scope.center.lng = latLng.lng;
            $scope.currentLocation = $scope.getHomeLocation($scope.center, addressFeature.feature.properties.label);
            $scope.invalidate(null, $scope.DEFAULT_ZOOM);
        };

        /* START: buttons for dismissible selected filters */
        //
        // key: custom ID
        // value: { modelName: ..., code: ... } - the structure may be changed later ...
        $scope.selectedFilterButtons = {};
        //
        $scope.addSelectedFilterButton = function (modelName, code) {
            $scope.selectedFilterButtons[modelName +':'+ code] = { modelName: modelName, code: code };
        };
        //
        $scope.removeSelectedFilterButton = function (modelName, code) {
            $scope.deselectFilterMenuItem(modelName, code);
            delete $scope.selectedFilterButtons[modelName +':'+ code];
        };
        //
        $scope.dismissFilterClick = function (modelName, code) {
            $scope.removeSelectedFilterButton(modelName, code);
            $scope.invalidate();
        };
        /* END: buttons for dismissible selected filters */

        $scope.toggleFilterMenu = function (modelName, status) {
            if (_.isUndefined(status)) {
                $scope.filterMenuConfigs[modelName].showList = !$scope.filterMenuConfigs[modelName].showList;
            } else {
                $scope.filterMenuConfigs[modelName].showList = status;
            }
        };

        $scope.onFilterMenuItemClick = function(modelName, code) {
            $scope.updateSelectedCount(modelName);

            if ($scope.isSelected(modelName, code)) {
                $scope.addSelectedFilterButton(modelName, code);
            } else {
                $scope.removeSelectedFilterButton(modelName, code);
            }

            $scope.invalidate();
        };

        $scope.updateSelectedCount = function(modelName) {
            $scope.filterMenuConfigs[modelName].selectedCount = $scope.getSelected(modelName).length;
        };

        $scope.hideExtendedFilters = true;
        $scope.hideExtendedFiltersButtonTitle = "facilities.show-all-filters";
        $scope.showAllFilters = function() {
            $scope.hideExtendedFilters = !$scope.hideExtendedFilters;
            $scope.hideExtendedFiltersButtonTitle =
                $scope.hideExtendedFiltersButtonTitle === "facilities.show-all-filters"
                    ? "facilities.hide-all-filters" : "facilities.show-all-filters";
        };

        $scope.clearFilter = function(modelName) {
            _.each($scope[modelName], function(item) {
                item.selected = false;
            });
            $scope.updateSelectedCount(modelName);
        };

        $scope.resetFilters = function() {
            _.map(_.keys($scope.filterMenuConfigs), function (modelName) {
                $scope.clearFilter(modelName);
            });

            $scope.selectedFilterButtons = {};

            $scope.searchText = $scope.text = '';
            $scope.invalidate();
        };

        $scope.getSelected = function(modelName) {
            return _.filter($scope[modelName], {selected: true});
        };
        $scope.getSelectedCodes = function(modelName) {
            return _.map($scope.getSelected(modelName), 'code');
        };

        $scope.setSelectedByCode = function(modelName, code) {
            _.find($scope[modelName], {code: code}).selected = true;
            $scope.addSelectedFilterButton(modelName, code);
        };
        if ($scope.searchParams.ageGroupCodes) {
            _.each($scope.searchParams.ageGroupCodes, function (ageGroupCode) {
                $scope.setSelectedByCode('lookupAgeGroups', ageGroupCode);
            });
            $scope.updateSelectedCount('lookupAgeGroups');
        }

        $scope.isSelected = function(modelName, code) {
            return _.find($scope[modelName], {code: code}).selected;
        };

        $scope.deselectFilterMenuItem = function (modelName, code) {
            _.find($scope[modelName], {code: code}).selected = false;
            $scope.updateSelectedCount(modelName);
        };

        $scope.addGeocoder = function () {
            if(!$scope.geocoder) {
                $scope.geocoder = GeocoderService.createGeocoder("geocoder", $scope.onSelectAddress);
            }
        };
        $scope.addGeocoder();

        $scope.toggleBodyContentConfig = chLayoutConfigFactory.layoutConfigState.toggleBodyContentConfig;
        $scope.$watch(function(){
            return chLayoutConfigFactory.layoutConfigState.isAsideVisible;
        }, function() {
            $scope.isAsideVisible = chLayoutConfigFactory.layoutConfigState.isAsideVisible;
            $scope.isContentFullWidth = chLayoutConfigFactory.layoutConfigState.isContentFullWidth;
        });

        $scope.openDefaultAddressModal = function(userProfile) {
            $uibModal.open({
                templateUrl: 'scripts/app/facilities/modal/default-address-dialog.html',
                controller: 'DefaultAddressModalCtrl',
                size: 'facilities-default-address',
                windowClass: 'ch-general-modal',
                resolve: {
                    userProfile: function() {
                        return userProfile;
                    }
                }
            }).result.then($scope.addressApplied, $scope.addressRejected);
        };

        $scope.addressApplied = function(addressFeature) {
            $scope.onSelectAddress(addressFeature);
        };
        $scope.addressRejected = function() {
            if (navigator.geolocation) {
                $log.debug('Geolocation is supported!');
                $scope.getGeoLocation();
            } else {
                $log.warn('Geolocation is not supported for this Browser/OS version yet.');
                $scope.getAddressFromProperties();
            }
        };


        $scope.getGeoLocation = function () {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    $scope.center.lat = position.coords.latitude;
                    $scope.center.lng = position.coords.longitude;
                    $scope.currentLocation = $scope.getHomeLocation($scope.center);
                },
                function () {
                    $scope.getAddressFromProperties();
                }
            );
        };

        $scope.getAddressFromProperties = function () {
            AppPropertiesService.defaultAddress(function (response) {
                var address = response.data;
                GeocoderService.searchAddress(address).then(
                    function (response) {
                        var data = response.data[0];
                        $scope.onSelectAddress({
                            latlng: {
                                lat: parseFloat(data.lat),
                                lng: parseFloat(data.lon)
                            },
                            feature: {
                                properties: {
                                    label: data.display_name
                                }
                            }
                        });
                    },
                    function() {
                        $log.warn('Cannot get address details');
                        $scope.getAddressFromProperties();
                    }
                );
            });
        };

        $scope.inkRipple = function() {
            var parent, ink, d, x, y;
            $(".ch-ink-btn").click(function(e){

                parent = $(this).parent();
                if(parent.find(".ink").length === 0)
                    $(".ch-ink-btn").append("<span class='ink'></span>");

                ink = parent.find(".ink");
                ink.removeClass("animate");

                //set size of .ink
                if(!ink.height() && !ink.width()) {
                    d = Math.max(parent.outerWidth(), parent.outerHeight());
                    ink.css({height: d, width: d});
                }

                x = e.pageX - parent.offset().left - ink.width()/2;
                y = e.pageY - parent.offset().top - ink.height()/2;

                ink.css({top: y+'px', left: x+'px'}).addClass("animate");
            })
        };

        $scope.profileHasEnoughAddressData = function (profile) {
            var hasPlace = !_.isNil(profile) && !_.isNil(profile.place);
            var hasLatLng = hasPlace && !_.isNil(profile.place.latitude) && !_.isNil(profile.place.longitude);
            var hasCityState = hasPlace && !_.isNil(profile.place.cityName) && !_.isNil(profile.place.state);
            return hasLatLng && hasCityState;
        };

        if ($scope.searchParams.latitude && $scope.searchParams.longitude) {
            $scope.onSelectAddress({
                latlng: {
                    lat: $scope.searchParams.latitude,
                    lng: $scope.searchParams.longitude
                },
                feature: {
                    properties: {
                        label: $scope.searchParams.geoLabel
                    }
                }
            });
        } else {
            Principal.identity().then(function (userProfile) {
                if (!$scope.profileHasEnoughAddressData(userProfile)) {
                    $scope.openDefaultAddressModal(userProfile);
                } else {
                    $scope.onSelectAddress({
                        latlng: {
                            lat: userProfile.place.latitude,
                            lng: userProfile.place.longitude
                        },
                        feature: {
                            properties: {
                                label: AddressUtils.formatAddress(userProfile.place)
                            }
                        }
                    });
                }
            });
        }
    }]);
