package io.github.cristaling.games.loveletter.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

public class User {

	@Id
	UUID uuid;
	String username;
	String password;

}
