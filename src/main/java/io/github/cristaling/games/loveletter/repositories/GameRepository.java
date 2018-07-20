package io.github.cristaling.games.loveletter.repositories;

import io.github.cristaling.games.loveletter.core.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

}
