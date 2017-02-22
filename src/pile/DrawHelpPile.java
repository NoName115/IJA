package src.pile;

import java.awt.Graphics;

import java.util.ArrayList;

import src.card.*;
import src.PlayGround;


public class DrawHelpPile extends Pile
{
	ArrayList<Card> cardList;

	public DrawHelpPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.cardList =  new ArrayList<Card>();
	}

	@Override
	public void setNewDefaultPosition()
	{
		for (Card c : this.cardList)
		{
			c.setDefaultPosition(this.xPosition, this.yPosition);
		}
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
	public void returnListOfCardsToPile(ListOfCards inputList)
	{
		if (inputList == null || inputList.isEmpty() || inputList.size() > 1)
		{
			return;
		}

		for (Card c : inputList.getList())
		{
			this.cardList.add(c);
		}
	}

	@Override
	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition
			);
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
}