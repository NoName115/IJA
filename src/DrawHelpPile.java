package src;

import java.awt.Graphics;

import java.util.ArrayList;


public class DrawHelpPile extends Pile
{
	ArrayList<Card> cardList;

	public DrawHelpPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.cardList =  new ArrayList<Card>();
	}

	public void setNewDefaultPosition()
	{
		for (Card c : this.cardList)
		{
			c.setDefaultPosition(this.xPosition, this.yPosition);
		}
	}

	public void update()
	{
		// NOTHING
	}

	public void render(Graphics g)
	{
		for (Card c : this.cardList)
		{
			c.render(g);
		}
	}

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

	public void returnCard(ListOfCards inputList)
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

	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition
			);
	}

	// return null if cardList is empty
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