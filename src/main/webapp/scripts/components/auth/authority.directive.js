'use strict';

angular.module('msapApp')
    .directive('hasAnyAuthority', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var setVisible = function () {
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    authorities = attrs.hasAnyAuthority.replace(/\s+/g, '').split(','),
                    defineVisibility = function (reset) {
                        var result;
                        if (reset) {
                            setVisible();
                        }

                        result = Principal.hasAnyAuthority(authorities);
                        if (result) {
                            setVisible();
                        } else {
                            setHidden();
                        }
                    };

                if (authorities.length > 0) {
                    defineVisibility(true);

                    scope.$watch(function() {
                        return Principal.isAuthenticated();
                    }, function() {
                        defineVisibility(true);
                    });
                }
            }
        };
    }])
    .directive('hasAuthority', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var setVisible = function () {
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    authority = attrs.hasAuthority.replace(/\s+/g, ''),
                    defineVisibility = function (reset) {

                        if (reset) {
                            setVisible();
                        }

                        Principal.hasAuthority(authority)
                            .then(function (result) {
                                if (result) {
                                    setVisible();
                                } else {
                                    setHidden();
                                }
                            });
                    };

                if (authority.length > 0) {
                    defineVisibility(true);

                    scope.$watch(function() {
                        return Principal.isAuthenticated();
                    }, function() {
                        defineVisibility(true);
                    });
                }
            }
        };
    }]);
