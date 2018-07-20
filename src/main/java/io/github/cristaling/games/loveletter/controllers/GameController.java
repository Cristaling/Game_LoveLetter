package io.github.cristaling.games.loveletter.controllers;

import io.github.cristaling.games.loveletter.core.Card;
import io.github.cristaling.games.loveletter.core.Game;
import io.github.cristaling.games.loveletter.core.Player;
import io.github.cristaling.games.loveletter.services.CardService;
import io.github.cristaling.games.loveletter.services.GameService;
import io.github.cristaling.games.loveletter.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	GameService gameService;
	PlayerService playerService;
	CardService cardService;

	@Autowired
	public GameController(GameService gameService, PlayerService playerService, CardService cardService) {
		this.gameService = gameService;
		this.playerService = playerService;
		this.cardService = cardService;
	}

	@RequestMapping("/create")
	public @ResponseBody
	UUID createGame() {
		return this.gameService.createGame();
	}

	@RequestMapping("/list")
	public @ResponseBody
	List<Game> gameList() {
		return this.gameService.getUnstartedGames();
	}

	@RequestMapping("/start")
	public void startGame(String gameToken) {
		UUID gameTokenUUID = UUID.fromString(gameToken);

		this.cardService.createDeck(gameTokenUUID);
		this.simpMessagingTemplate.convertAndSend("/events/game/" + gameToken + "/start", "");

		List<Player> players = this.playerService.getPlayersByGameToken(gameTokenUUID);
		for (Player player : players) {
			Card drew = this.cardService.drawTopCard(gameTokenUUID, player.getUuid());
			simpMessagingTemplate.convertAndSend("/events/player/drew/" + player.getUuid(), drew);
			System.out.println(player.getUsername() + " drew " + drew.getName());
		}

		Player player = players.get(0);
		Card drew = this.cardService.drawTopCard(gameTokenUUID, player.getUuid());
		simpMessagingTemplate.convertAndSend("/events/player/drew/" + player.getUuid(), drew);
		System.out.println(player.getUsername() + " drew " + drew.getName());
	}

	@RequestMapping("/play")
	public void playCard(String cardToken) {
		UUID cardTokenUUID = UUID.fromString(cardToken);
		UUID playerToken = this.cardService.playCard(cardTokenUUID);
		if (playerToken != null) {
			simpMessagingTemplate.convertAndSend("/events/player/ask/" + playerToken, "");
		}
	}

}
