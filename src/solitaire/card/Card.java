package solitaire.card;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import solitaire.pile.DeckPile;
import solitaire.PlayGround;


public class Card
{
	private int value;		// 1 - A, 2 - 2 ..., 11 - J, 12 - Q, 13 - K
	private DeckPile.Type type;

	public boolean isReaveled() {
		return isReaveled;
	}

	private boolean isReaveled;

	//private Image

	public Card(int cardValue, DeckPile.Type cardType)
	{
		this.value = cardValue;
		this.type = cardType;

		this.isReaveled = false;
	}

	@Override
	public String toString()
	{
		String cardInString;
		switch(this.value)
		{
			case 1:
				cardInString = "A";
				break;
			case 11:
				cardInString = "J";
				break;
			case 12:
				cardInString = "Q";
				break;
			case 13:
				cardInString = "K";
				break;
			default:
				cardInString = Integer.toString(this.value);
				break;
		}

		return cardInString + " - " + this.type.toString();
	}

	public DeckPile.Type getType()
	{
		return this.type;
	}

	public int getValue()
	{
		return this.value;
	}

	public boolean typeEqual(Card inputCard)
	{
		return this.type == inputCard.getType();
	}

	public boolean colorEqual(Card inputCard)
	{
		return this.type.equalColor(inputCard.getType());
	}

	public void faceUp() { this.isReaveled = true; }
	public void faceDown() { this.isReaveled = false; }

}
