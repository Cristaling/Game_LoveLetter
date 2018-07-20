package io.github.cristaling.games.loveletter.config.security;

import java.security.Principal;

public class ExoPrincipal implements Principal {

	String publicToken;

	ExoPrincipal(String publicToken) {
		this.publicToken = publicToken;
	}

	@Override
	public String getName() {
		return publicToken;
	}

}
