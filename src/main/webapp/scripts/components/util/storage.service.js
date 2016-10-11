'use strict';

angular.module('msapApp')
    .service('StorageService', function ($window) {
        function get(repo, key) {
            return $window[repo].getItem(key);
        }

        function save(repo, key, data) {
            $window[repo].setItem(key, data);
        }

        function remove(repo, key) {
            $window[repo].removeItem(key);
        }

        function clearAll(repo) {
            $window[repo].clear();
        }

        return {
            getLocal: function (key) {
                return get('localStorage', key);
            },

            saveLocal: function (key, data) {
                return save('localStorage', key, JSON.stringify(data));
            },

            removeLocal: function (key) {
                return remove('localStorage', key);
            },

            clearAllLocal : function () {
                return clearAll('localStorage');
            },

            getSession: function (key) {
                return get('sessionStorage', key);
            },

            saveSession: function (key, data) {
                return save('sessionStorage', key, JSON.stringify(data));
            },

            removeSession: function (key) {
                return remove('sessionStorage', key);
            },

            clearAllSession : function () {
                return clearAll('sessionStorage');
            }
        };
    });
