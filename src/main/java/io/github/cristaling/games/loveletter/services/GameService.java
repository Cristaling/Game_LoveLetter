package io.github.cristaling.games.loveletter.services;

import io.github.cristaling.games.loveletter.core.Game;
import io.github.cristaling.games.loveletter.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

	GameRepository gameRepository;

	@Autowired
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public UUID createGame() {
		Game game=new Game();
		game.setUuid(UUID.randomUUID());
		game.setCurrentPlayer(0);
		this.gameRepository.save(game);
		return game.getUuid();
	}

	public List<Game> getUnstartedGames() {
		return this.gameRepository.findAll();
	}
}
