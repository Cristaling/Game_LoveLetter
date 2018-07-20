package io.github.cristaling.games.loveletter.services;

import io.github.cristaling.games.loveletter.core.Player;
import io.github.cristaling.games.loveletter.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PlayerService {

	PlayerRepository playerRepository;

	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public Player joinGame(UUID gameToken, String username) {

		Player player = new Player();
		List<Player> players = getPlayersByGameToken(gameToken);

		player.setUuid(UUID.randomUUID());
		player.setGame(gameToken);
		player.setHearths(0);
		player.setElim(false);
		player.setProt(false);

		if (players.size() >= 4) {
			player.setHearths(-1);
			return player;
		}

		if (username.length() < 3) {
			username = "Player" + new Random().nextInt(100);
		}

		player.setUsername(username);
		this.playerRepository.save(player);
		return player;
	}

	public List<Player> getPlayersByGameToken(UUID gameToken) {
		//return this.playerRepository.findAll();
		List<Player> result = this.playerRepository.getPlayersByGame(gameToken);

		result.sort(new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return o1.getUuid().compareTo(o2.getUuid());
			}
		});

		return result;
	}
}
