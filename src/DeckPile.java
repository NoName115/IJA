package src;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


// Trieda pre vygenerovanie celeho balicka kariet
public class DeckPile extends Pile
{
	private ArrayList<Card> deck;

	private static String[] numbers = new String[] {
		"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
	};
	private static String[] types = new String[] {
		"heart", "diamond", "spade", "club"
	};

	// Generuje deck o 52 kartach
	public DeckPile()
	{
		this.deck = new ArrayList<Card>();

		// Create whole deck
		for (int i = 0; i < types.length; ++i)
		{
			for (int j = 0; j < numbers.length; ++j)
			{
				deck.add(new Card(numbers[j], types[i]));
			}
		}
	}

	public void update() {}
	public void render(Graphics g) {}
	public void addCard(Card inputCard) {}

	// Return random card from deck
	// return null if deck is empty
	public Card popCard()
	{
		if (deck.isEmpty())
		{
			return null;
		}

		Random ran = new Random();
		int randomIndex = ran.nextInt(deck.size());
		Card returnCard = deck.get(randomIndex);
		deck.remove(randomIndex);

		return returnCard;
	}
}