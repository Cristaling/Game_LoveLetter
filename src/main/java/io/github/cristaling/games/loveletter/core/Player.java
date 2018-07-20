package io.github.cristaling.games.loveletter.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Player {

	@Id
	UUID uuid;
	UUID game;
	String username;
	int hearths;
	boolean elim;
	boolean prot;

	public boolean isElim() {
		return elim;
	}

	public void setElim(boolean elim) {
		this.elim = elim;
	}

	public boolean isProt() {
		return prot;
	}

	public void setProt(boolean prot) {
		this.prot = prot;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getGame() {
		return game;
	}

	public void setGame(UUID game) {
		this.game = game;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHearths() {
		return hearths;
	}

	public void setHearths(int hearths) {
		this.hearths = hearths;
	}
}
