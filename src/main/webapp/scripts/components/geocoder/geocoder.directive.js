angular.module('msapApp')
    .directive('addGeocoderSearchField', function() {
        return {
            restrict: 'A',
            link: function(scope) {
                scope.addGeocoder();
            }
        };
});
