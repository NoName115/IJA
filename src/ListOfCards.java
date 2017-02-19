package src;

import java.awt.Graphics;
import java.util.ArrayList;


public class ListOfCards
{
	private static final int Y_CARD_SHIFT = 30;

	private ArrayList<Card> listOfCards;
	private boolean isDragged;

	public ListOfCards(ArrayList<Card> inputCardList, Card inputCard)
	{
		this.listOfCards = inputCardList;
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

	public void setIsDragged(boolean iBool)
	{
		for (Card c : this.listOfCards)
		{
			c.setIsDragged(iBool);
		}
	}

	public void setActualPosition(int x, int y)
	{
		for (int i = 0; i < this.listOfCards.size(); ++i)
		{
			this.listOfCards.get(i).setActualPosition(x, y + i * Y_CARD_SHIFT);
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