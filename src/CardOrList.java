package src;

import java.awt.Graphics;
import java.util.ArrayList;


public class CardOrList
{
	private static final int Y_CARD_SHIFT = 30;

	private Card card;
	private ArrayList<Card> listOfCards;

	private boolean isDragged;

	public CardOrList(Card inputCard, ArrayList<Card> inputCardList)
	{
		this.card = inputCard;
		this.listOfCards = inputCardList;
	}

	public void render(Graphics g)
	{
		if (this.listOfCards != null)
		{
			for (Card c : this.listOfCards)
			{
				c.render(g);
			}
		}

		if (this.card != null)
		{
			this.card.render(g);
		}
	}

	public void setIsDragged(boolean iBool)
	{
		if (this.listOfCards != null)
		{
			for (Card c : this.listOfCards)
			{
				c.setIsDragged(iBool);
			}
		}

		if (this.card != null)
		{
			this.card.setIsDragged(iBool);
		}
	}

	public void setActualPosition(int x, int y)
	{
		if (this.listOfCards != null)
		{
			int counter = 0;
			for (Card c : this.listOfCards)
			{
				c.setActualPosition(x, y + counter * Y_CARD_SHIFT);
				counter++;
			}
		}

		if (this.card != null)
		{
			this.card.setActualPosition(x, y);
		}
	}

	public void printDebug()
	{
		if (this.listOfCards != null)
		{
			for (Card c : this.listOfCards)
			{
				c.printDebug();
			}

			System.out.println("--------");
		}

		if (this.card != null)
		{
			this.card.printDebug();

			System.out.println("--------");
		}
	}

	public Card getCard()
	{
		return this.card;
	}

	public ArrayList<Card> getList()
	{
		return this.listOfCards;
	}
}