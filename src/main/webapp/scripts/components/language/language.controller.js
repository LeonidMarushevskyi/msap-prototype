'use strict';

angular.module('msapApp')
    .controller('LanguageController', function ($scope, $translate, Language, tmhDynamicLocale) {
        $scope.changeLanguage = function (languageKey) {
            $translate.use(languageKey);
            tmhDynamicLocale.set(languageKey);
        };

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
            $scope.restoreSelectedLanguage();
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
                "zh-cn": "中文（简体）",
                "zh-tw": "繁體中文"
            }[lang];
        }
    });
