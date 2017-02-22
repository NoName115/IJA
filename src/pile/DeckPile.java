package src.pile;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import src.card.*;
import src.PlayGround;


/**
 * Trieda pre vygenerovanie celeho balicka kariet
 */
public class DeckPile extends Pile
{
	private static String[] numbers = new String[] {
		"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
	};
	private static String[] types = new String[] {
		"heart", "diamond", "spade", "club"
	};

	/**
	 * Generuje balicek 52 kariet
	 */
	public DeckPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		for (int i = 0; i < this.types.length; ++i)
		{
			for (int j = 0; j < this.numbers.length; ++j)
			{
				this.cardList.add(new Card(this.numbers[j], this.types[i], this.pg));
			}
		}
	}

	public void update() {}
	public void render(Graphics g) {}
	public void addCard(Card inputCard) {}
	public void setNewDefaultPosition() {}

	/**
	 * Vrati nahodnu kartu z balicka
	 * Vrati null ak je balicek prazny
	 */
	public Card popCard()
	{
		if (this.cardList.isEmpty())
		{
			return null;
		}

		Random ran = new Random();
		int randomIndex = ran.nextInt(this.cardList.size());
		Card returnCard = this.cardList.get(randomIndex);
		this.cardList.remove(randomIndex);

		return returnCard;
	}
}