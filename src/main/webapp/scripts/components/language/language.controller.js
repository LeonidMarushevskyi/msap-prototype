'use strict';

angular.module('msapApp')
    .controller('LanguageController', function ($scope, $translate, Language, LanguageUtils, tmhDynamicLocale, Principal) {
        $scope.changeLanguage = LanguageUtils.changeUILanguage;

        $scope.getCurrentLanguageKey = function () {
            return _.isUndefined(tmhDynamicLocale.get()) ? 'en' : tmhDynamicLocale.get();
        };

        $scope.restoreSelectedLanguage = function () {
            $translate.use($scope.getCurrentLanguageKey());
        };

        $scope.clickLanguage = function (languageKey) {
            $scope.changeLanguage(languageKey);
            $scope.isLanguagePopupShown = false;
        };

        Language.getAll().then(function (languages) {
            $scope.languages = languages;
        });

        $scope.isLanguagePopupShown = false;
        $scope.toggleLanguagePopup = function() {
            $scope.isLanguagePopupShown = !$scope.isLanguagePopupShown;
        };

        $scope.isCurrent = function (languageKey) {
            return $scope.getCurrentLanguageKey() == languageKey;
        };

        $scope.init = function () {
            Principal.identity().then(function(account) {
                if (_.isNil(account) || _.isNil(account.langKey)) {
                    // not logged-in or preferred language is undefined
                    $scope.restoreSelectedLanguage();
                } else {
                    // logged-in and preferred language is defined
                    $scope.changeLanguage(account.langKey);
                }
            });
        };
        $scope.init();
    })
    .filter('findLanguageFromKey', function () {
        return function (lang) {
            return {
                "ca": "Català",
                "da": "Dansk",
                "de": "Deutsch",
                "en": "English",
                "es": "Español",
                "fr": "Français",
                "gl": "Galego",
                "hu": "Magyar",
                "it": "Italiano",
                "ja": "日本語",
                "ko": "한국어",
                "nl": "Nederlands",
                "pl": "Polski",
                "pt-br": "Português (Brasil)",
                "pt-pt": "Português",
                "ro": "Română",
                "ru": "Русский",
                "sv": "Svenska",
                "ta": "தமிழ்",
                "tr": "Türkçe",
                "vi": "Tiếng Việt",
                "zh-cn": "中文（简体）",
                "zh-tw": "繁體中文"
            }[lang];
        }
    });
