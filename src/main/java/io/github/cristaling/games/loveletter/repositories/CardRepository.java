package io.github.cristaling.games.loveletter.repositories;

import io.github.cristaling.games.loveletter.core.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

	List<Card> getCardsByGameAndPlayer(UUID game, UUID player);

}
