import java.util.ArrayList;
import java.util.Random;

public class Deck {

	// Card[] card;
	private ArrayList<Card> cardDeck;

	public Deck() { // constructor - deck of cards that holds cards with its own rank and suit
		ArrayList<Card> cardDeck = new ArrayList<Card>();
		this.cardDeck = cardDeck;
	}

	public void shuffle() {

		// concatenate
		for (int i = 0; i < 4; i++) { // 4 suits represented by ints
			for (int j = 1; j < 14; j++) { // 13 ranks represented by ints
				Card newCard = new Card(j, i);
				cardDeck.add(newCard);
			}
		}

		for (int i = 0; i < 52; i++) { // 52 cards in deck
			Random random = new Random(); // Randomly select a slot
			int randomIdx = random.nextInt(52);
			Card placeholder = cardDeck.get(randomIdx);
			cardDeck.set(randomIdx, cardDeck.get(i)); // shuffle numbers
			cardDeck.set(i, placeholder);
		}
	}

	public void removeCard() {
		cardDeck.remove(0); // removes first card after play
	}

	public String nextCard() {
		Card nextCard = cardDeck.get(0); // always gets the first card in deck

		int rank = nextCard.getRank(); // to turn the ints to strings to show user
		String cardRank = " ";
		if (rank == 1) {
			cardRank = "A";
		} else if (rank == 11) {
			cardRank = "J";
		} else if (rank == 12) {
			cardRank = "Q";
		} else if (rank == 13) {
			cardRank = "K";
		} else
			cardRank = String.valueOf(rank);

		String cardSuit = " ";
		int suit = nextCard.getSuit(); // turn ints to strings to show user
		if (suit == 0) {
			cardSuit = "C";
		} else if (suit == 1) {
			cardSuit = "D";
		} else if (suit == 2) {
			cardSuit = "H";
		} else if (suit == 3) {
			cardSuit = "S";
		}

		String cardFace = cardRank + cardSuit; // concatenate the rank and suit
		return cardFace;
	}

	public Card get(int i) {
		// to get card from array
		return cardDeck.get(i);
	}
}
