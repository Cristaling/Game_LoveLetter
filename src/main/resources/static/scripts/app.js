(function () {
    'use strict'

    var app = angular.module('LoveLetter', ['ngRoute', 'ngAnimate', 'ngCookies', 'ngAria', 'ngMaterial', 'ngMessages']);

    app.config(function ($routeProvider) {
        $routeProvider
            .when("/landing", {
                templateUrl: "views/LandingPage.html",
                controller: 'LandingController',
                controllerAs: 'ctrl'
            })
            .when("/game/:gameToken", {
                templateUrl: "views/GamePage.html",
                controller: 'GameController',
                controllerAs: 'ctrl'
            })
            .otherwise({redirectTo: "/landing"});
    });

    app.config(function ($mdThemingProvider) {
        $mdThemingProvider.theme('default')
            .dark();
    });

})();