package src.card;

import java.awt.Graphics;
import java.util.ArrayList;


public class ListOfCards
{
	private int Y_CARD_SHIFT;
	private ArrayList<Card> listOfCards;
	private boolean isDragged;

	// 0 index je karta na spodku balicku
	public ListOfCards(ArrayList<Card> inputCardList, Card inputCard, int cardShift)
	{
		this.listOfCards = inputCardList;
		this.Y_CARD_SHIFT = cardShift;
		this.isDragged = false;

		if (inputCard != null)
		{
			this.listOfCards = new ArrayList<Card>();
			this.listOfCards.add(inputCard);
		}
	}

	public void render(Graphics g)
	{
		for (Card c : this.listOfCards)
		{
			c.render(g);
		}
	}

	public void setIsDragged(boolean iBool, int x, int y)
	{
		for (Card c : this.listOfCards)
		{
			c.setIsDragged(iBool);
		}

		if (iBool)
		{
			this.listOfCards.get(0).setDifPosition(x, y);
		}
	}

	public void setActualPosition(int x, int y)
	{
		if (this.listOfCards.isEmpty())
		{
			return;
		}

		this.listOfCards.get(0).setHandlePosition(x, y);
		int xPos = this.listOfCards.get(0).getHandleXPos(x);
		int yPos = this.listOfCards.get(0).getHandleYPos(y);

		for (int i = 1; i < this.listOfCards.size(); ++i)
		{
			this.listOfCards.get(i).setActualPosition(
				xPos,
				yPos + i * Y_CARD_SHIFT
				);
		}
	}

	public ArrayList<Card> getList()
	{
		return this.listOfCards;
	}

	public boolean isEmpty()
	{
		return this.listOfCards.isEmpty();
	}

	public int size()
	{
		return this.listOfCards.size();
	}


	public void printDebug()
	{
		for (Card c : this.listOfCards)
		{
			c.printDebug();
		}

		System.out.println("--------");
	}
}