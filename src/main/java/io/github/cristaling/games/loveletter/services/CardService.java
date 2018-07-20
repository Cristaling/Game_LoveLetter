package io.github.cristaling.games.loveletter.services;

import io.github.cristaling.games.loveletter.core.Card;
import io.github.cristaling.games.loveletter.repositories.CardRepository;
import io.github.cristaling.games.loveletter.utils.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CardService {

	CardRepository cardRepository;

	@Autowired
	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	public void createDeck(UUID gameToken) {
		this.cardRepository.save(generateCardOfType(gameToken, CardType.PRINCESS));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.COUNTESS));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.KING));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.PRINCE));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.PRINCE));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.HANDMAIDEN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.HANDMAIDEN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.BARON));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.BARON));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.PRIEST));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.PRIEST));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.GUARDIAN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.GUARDIAN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.GUARDIAN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.GUARDIAN));
		this.cardRepository.save(generateCardOfType(gameToken, CardType.GUARDIAN));
	}

	public Card drawTopCard(UUID gameToken, UUID playerToken) {
		List<Card> deck = this.cardRepository.getCardsByGameAndPlayer(gameToken, null);

		if (deck.size() < 1) {
			return null;
		}

		deck.sort(new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) {
				return o1.getUuid().compareTo(o2.getUuid());
			}
		});

		Card result = deck.get(0);
		result.setPlayer(playerToken);
		this.cardRepository.save(result);
		deck.remove(0);
		return result;
	}

	public Card generateCardOfType(UUID gameToken, CardType cardType) {
		Card card = new Card();
		card.setUuid(UUID.randomUUID());
		card.setGame(gameToken);
		card.setCardType(cardType);
		card.setPlayed(false);
		card.setName(cardType.getName());
		card.setValue(cardType.getValue());
		card.setLore(cardType.getLore());
		return card;
	}

	public UUID playCard(UUID cardToken) {

		Card card = this.cardRepository.getOne(cardToken);
		System.out.println("Nigga played " + card.getName());
		return card.getPlayer();

	}
}
