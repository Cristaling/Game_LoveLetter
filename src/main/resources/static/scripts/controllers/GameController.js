(function () {
    'use strict'

    var app = angular.module('LoveLetter');

    app.controller('GameController', ['$location', '$http', '$routeParams', '$scope', function ($location, $http, $routeParams, $scope) {
        var vm = this;

        vm.gameToken = $routeParams["gameToken"];
        vm.username = "";
        vm.cards = [];
        vm.gameState = 0;

        vm.connectToGame = function () {
            vm.socket = new SockJS('/ll-websocket');
            vm.stompClient = Stomp.over(vm.socket);
            vm.stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                vm.stompClient.subscribe('/events/game/' + vm.gameToken + '/player/new', function (response) {
                    var data = JSON.parse(response.body);
                    vm.players.push(data);
                    $scope.$apply();
                });
                vm.stompClient.subscribe('/events/game/' + vm.gameToken + '/start', function (response) {
                    //var data = JSON.parse(response.body);
                    vm.gameState = 1;
                    $scope.$apply();
                });
                vm.stompClient.subscribe('/events/player/ask/' + vm.myData.uuid, function (response) {
                    //var data = JSON.parse(response.body);
                    vm.gameState = 2;
                    $scope.$apply();
                });
                vm.stompClient.subscribe('/events/player/drew/' + vm.myData.uuid, function (response) {
                    var data = JSON.parse(response.body);
                    vm.cards.push(data);
                    $scope.$apply();
                });
            });
        };

        vm.joinGame = function () {
            $http.get("/api/player/join?gameToken=" + vm.gameToken + "&username=" + vm.username).then(function (value) {
                if (value.data.hearths < 0) {
                    alert("Room is full!");
                    $location.path("/landing");
                }
                vm.myData = value.data;
                vm.connectToGame();
            }, function (reason) {
                alert(reason);
            })
        };

        vm.refreshPlayerList = function () {
            $http.get("/api/player/bygame?gameToken=" + vm.gameToken).then(function (value) {
                vm.players = value.data;
                vm.joinGame();
            }, function (reason) {
                alert(reason);
            })
        };

        vm.startGame = function () {
            $http.get("/api/game/start?gameToken=" + vm.gameToken).then(function (value) {

            }, function (reason) {
                alert(reason);
            })
        };

        vm.playCard = function (card) {
            if (vm.cards.length < 2) {
                return;
            }
            $http.get("/api/game/play?cardToken=" + card.uuid).then(function (value) {
                var index = vm.cards.indexOf(card);
                vm.cards.splice(index, 1);
            }, function (reason) {
                alert(reason);
            })
        };
        
        vm.choosePlayer = function (player) {
            $http.get("/api/game/choose?playerToken=" + player.uuid).then(function (value) {
                vm.gameState = 1;
            }, function (reason) {
                alert(reason);
            })
        };

        vm.refreshPlayerList();

    }]);
})();