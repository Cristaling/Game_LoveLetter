package io.github.cristaling.games.loveletter.utils;

public enum CardType {

	GUARDIAN("Guardian", "You eliminate a player if you can guess his card", 1),
	PRIEST("Priest", "Check another player's card", 2),
	BARON("Baron", "You compare your card with another player's. The player with the lowest card value is eliminated", 3),
	HANDMAIDEN("Handmaiden", "You are protected until your next turn", 4),
	PRINCE("Prince", "Force another player to play his card", 5),
	KING("King", "You swap cards with another player", 6),
	COUNTESS("Countess", "If you also have Price or King, you have to play this card", 7),
	PRINCESS("Princess", "If you play this card, you are eliminated", 8);

	String name;
	String lore;
	int value;

	CardType(String name, String lore, int value) {
		this.name = name;
		this.lore = lore;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getLore() {
		return lore;
	}

	public int getValue() {
		return value;
	}
}
