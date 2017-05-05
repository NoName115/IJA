package solitaire.pile;

import java.awt.Graphics;

import java.util.ArrayList;

import solitaire.card.*;
import solitaire.PlayGround;


public class DrawHelpPile extends Pile
{
	public DrawHelpPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g)
	{
		for (Card c : this.cardList)
		{
			c.render(g);
		}
	}

	/**
	 * Vrati jednu vrchnu(poslednu) kartu
	 */
	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		if (this.cardList.isEmpty())
		{
			return null;
		}

		int lastIndex = this.cardList.size() - 1;
		Card tempCard = this.cardList.get(lastIndex);
		this.cardList.remove(lastIndex);

		return new ListOfCards(null, tempCard, this.pg.getCardShift());
	}

	@Override
	public void returnListOfCardsToPile(ListOfCards inputList, boolean action)
	{
		if (inputList == null || inputList.isEmpty() || inputList.size() > 1)
		{
			return;
		}

		for (Card c : inputList.getList())
		{
			this.cardList.add(c);
			c.setDefaultPosition(this.xPosition, this.yPosition);
		}
	}

	/**
	 * Vrati null ak je list kariet prazny
	 * Inac vrati vrchnu(poslednu) kartu
	 */
	public Card getLastCard()
	{
		if (this.cardList.isEmpty())
		{
			return null;
		}

		int lastIndex = this.cardList.size() - 1;
		Card tempCard = this.cardList.get(lastIndex);
		this.cardList.remove(lastIndex);

		return tempCard;
	}

	public ArrayList<Card> getCardList()
	{
		return this.cardList;
	}
}