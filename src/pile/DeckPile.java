package src.pile;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import src.card.*;
import src.PlayGround;


/**
 * Trieda pre vygenerovanie celeho balicka kariet
 */
public class DeckPile extends Pile
{
	private static String[] numberList = new String[] {
		"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
	};
	public static enum Type
	{
		CLUBS, DIAMONDS, HEARTS, SPADES;

		public Color toColor()
		{
			switch(this)
			{
				case DIAMONDS:
				case HEARTS:
					return Color.RED;
				case CLUBS:
				case SPADES:
					return Color.BLACK;
				default:
					return Color.WHITE;
			}
		}

		public String toString()
		{
			switch(this)
			{
				case DIAMONDS:
					return "D";
				case HEARTS:
					return "H";
				case CLUBS:
					return "C";
				case SPADES:
					return "S";
				default:
					return "Error";
			}
		}
	}

	/**
	 * Generuje balicek 52 kariet
	 */
	public DeckPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		for (DeckPile.Type t : DeckPile.Type.values())
		{
			for (int j = 0; j < this.numberList.length; ++j)
			{
				this.cardList.add(new Card(this.numberList[j], t, this.pg));
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