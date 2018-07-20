package io.github.cristaling.games.loveletter.controllers;

import io.github.cristaling.games.loveletter.core.Player;
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
@RequestMapping("/api/player")
public class PlayerController {

	SimpMessagingTemplate simpMessagingTemplate;

	PlayerService playerService;

	@Autowired
	public PlayerController(SimpMessagingTemplate simpMessagingTemplate, PlayerService playerService) {
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.playerService = playerService;
	}

	@RequestMapping("/join")
	public @ResponseBody
	Player joinGame(String gameToken, String username) {
		UUID gameTokenUUID = UUID.fromString(gameToken);
		Player player = this.playerService.joinGame(gameTokenUUID, username);
		this.simpMessagingTemplate.convertAndSend("/events/game/" + gameToken + "/player/new", player);
		return player;
	}

	@RequestMapping("/bygame")
	public @ResponseBody
	List<Player> getPlayersByGameToken(String gameToken) {
		UUID gameTokenUUID = UUID.fromString(gameToken);
		return this.playerService.getPlayersByGameToken(gameTokenUUID);
	}

}
