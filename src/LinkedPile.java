package src;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;


public class LinkedPile extends Pile
{
	private static final int Y_CARD_SHIFT = 30;
	private ArrayList<Card> unReaveledCardList;
	private ArrayList<Card> reaveledCardList;

	private static int defaultHeight;

	public LinkedPile(int xPos, int yPos, int width, int height)
	{
		super(xPos, yPos, width, height);

		this.defaultHeight = height;
		this.unReaveledCardList = new ArrayList<Card>();
		this.reaveledCardList = new ArrayList<Card>();
	}

	public void update()
	{
		// NOTHING
	}

	// Render vsetkych kariet
	// Najprv sa renderuje unReaveledCardList
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.defaultHeight);

		for (Card c : this.unReaveledCardList)
		{
			c.render(g);
		}

		for (Card c : this.reaveledCardList)
		{
			c.render(g);
		}
	}

	// Vytvorit objekt ktory obsahuje Card a ArrayList
	public ListOfCards selectPile(int ix, int iy)
	{
		if (this.reaveledCardList.isEmpty())
		{
			return null;
		}

		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			int startYPos = this.reaveledCardList.get(0).getYDefaultPosition();
			int endYPos = startYPos + (this.reaveledCardList.size() - 1) * Y_CARD_SHIFT + this.defaultHeight;

			ListOfCards tempList = null;

			if (startYPos <= iy && endYPos >= iy)
			{
				int summaryYShift = (this.reaveledCardList.size() - 1 + this.unReaveledCardList.size()) * Y_CARD_SHIFT + this.yPosition;

				// Vrati zoznam kariet do index po koniec zoznamu
				if (iy < summaryYShift)
				{
					int index = (iy - this.yPosition - this.unReaveledCardList.size() * Y_CARD_SHIFT) / Y_CARD_SHIFT;
					int listSize = this.reaveledCardList.size();

					ArrayList<Card> outputCardList = new ArrayList<Card>();

					for (int i = index; i < listSize; ++i)
					{
						outputCardList.add(this.reaveledCardList.get(index));
						this.reaveledCardList.remove(index);
					}

					tempList = new ListOfCards(outputCardList, null);
				}
				// Vrati len 1, poslednu kartu
				else if (iy >= summaryYShift && iy <= (summaryYShift + this.defaultHeight))
				{
					int lastCardIndex = this.reaveledCardList.size() - 1;
					tempList = new ListOfCards(null, this.reaveledCardList.get(lastCardIndex));
					this.reaveledCardList.remove(lastCardIndex);
				}

				this.calculateNewHeight();
				return tempList;
			}
		}

		return null;
	}

	// insertListOfCards
	public boolean insertCard(ListOfCards inputList)
	{
		if (inputList == null || inputList.isEmpty())
		{
			return false;
		}

		int counter = 0;
		for (Card c : inputList.getList())
		{
			c.setDefaultPosition(
				this.xPosition,
				this.yPosition + (this.unReaveledCardList.size() + this.reaveledCardList.size()) * Y_CARD_SHIFT
				);
			this.reaveledCardList.add(c);
		}

		this.calculateNewHeight();
		return true;
	}

	// returnListOfCardsToPile
	public void returnCard(ListOfCards inputList)
	{
		if (inputList == null)
		{
			return;
		}

		for (Card c : inputList.getList())
		{
			c.setDefaultPosition(
				this.xPosition,
				this.yPosition + (this.unReaveledCardList.size() + this.reaveledCardList.size()) * Y_CARD_SHIFT
				);
			this.reaveledCardList.add(c);
		}

		this.calculateNewHeight();
	}

	// Otoci vrchnu kartu z unReaveledCardList
	public void actionEnded()
	{
		if (unReaveledCardList.isEmpty() || reaveledCardList.size() >= 1)
		{
			return;
		}

		// Reavel top card
		int lastCardIndex = this.unReaveledCardList.size() - 1;
		Card tempCard = this.unReaveledCardList.get(lastCardIndex);
		this.unReaveledCardList.remove(lastCardIndex);
		this.reaveledCardList.add(tempCard);
		tempCard.faceUp();
	}

	public void addCard(Card inputCard)
	{
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition + Y_CARD_SHIFT * this.unReaveledCardList.size()
			);
		this.unReaveledCardList.add(inputCard);
		this.calculateNewHeight();
	}

	private void calculateNewHeight()
	{
		int sizeOfLists = this.unReaveledCardList.size() + this.reaveledCardList.size();
		this.height = sizeOfLists > 1 ? (sizeOfLists - 1) * Y_CARD_SHIFT + this.defaultHeight : this.defaultHeight;
	}
}
