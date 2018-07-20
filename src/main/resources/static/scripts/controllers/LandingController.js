(function(){
    'use strict'

    var app = angular.module('LoveLetter');

    app.controller('LandingController', ['$location', '$http', '$cookies', '$scope', function($location, $http, $cookies, $scope)
    {
        var vm = this;

        vm.createRoom = function () {
            $http.get("/api/game/create").then(function (value) {
                $location.path("/game/" + value.data);
            }, function (reason) {
                alert(reason);
            });
        };

        vm.joinRoom = function () {
            $location.path("/game/" + vm.gameToken);
        };

    }]);
})();