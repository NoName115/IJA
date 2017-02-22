package src.pile;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

import src.card.*;
import src.PlayGround;


public class LinkedPile extends Pile
{
	private int Y_CARD_SHIFT;
	private ArrayList<Card> unReaveledCardList;
	private ArrayList<Card> reaveledCardList;

	// Obsahuje zakladnu vysku Pile-u
	private static int defaultHeight;

	public LinkedPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.defaultHeight = height;
		this.unReaveledCardList = new ArrayList<Card>();
		this.reaveledCardList = new ArrayList<Card>();
		this.Y_CARD_SHIFT = this.pg.getCardShift();
	}

	public void update() {}

	/**
	 * Najprv sa renderuje unReaveledCardList a tak reaveled
	 */
	@Override
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

	@Override
	public void setNewResolution(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;

		this.defaultHeight = height;
		this.calculateNewHeight();

		this.setNewDefaultPosition();
	}

	@Override
	public void setNewDefaultPosition()
	{
		for (int i = 0; i < this.unReaveledCardList.size(); ++i)
		{
			this.unReaveledCardList.get(i).setDefaultPosition(
				this.xPosition,
				this.yPosition + i * this.Y_CARD_SHIFT
				);
		}

		for (int i = 0; i < this.reaveledCardList.size(); ++i)
		{
			this.reaveledCardList.get(i).setDefaultPosition(
				this.xPosition,
				this.yPosition + (i + this.unReaveledCardList.size()) * this.Y_CARD_SHIFT
				);
		}
	}

	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		if (this.reaveledCardList.isEmpty())
		{
			return null;
		}

		// Nachadzam sa v casti odhalenych kariet
		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			// Horny a Dolny bod zoznamu odhalenych kariet
			int startYPos = this.reaveledCardList.get(0).getYDefaultPosition();
			int endYPos = startYPos + (this.reaveledCardList.size() - 1) * Y_CARD_SHIFT + this.defaultHeight;

			ListOfCards tempList = null;
			if (startYPos <= iy && endYPos >= iy)
			{
				// Celkova vyska odhaleneho balicka kariet
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

					tempList = new ListOfCards(outputCardList, null, Y_CARD_SHIFT);
				}
				// Vrati len 1, poslednu kartu (vrchnu kartu)
				else if (iy >= summaryYShift && iy <= (summaryYShift + this.defaultHeight))
				{
					int lastCardIndex = this.reaveledCardList.size() - 1;
					tempList = new ListOfCards(null, this.reaveledCardList.get(lastCardIndex), Y_CARD_SHIFT);
					this.reaveledCardList.remove(lastCardIndex);
				}

				this.calculateNewHeight();
				return tempList;
			}
		}

		return null;
	}

	@Override
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

	@Override
	public void returnListOfCardsToPile(ListOfCards inputList)
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

	/**
	 * Otoci vrchnu kartu z unReaveledCardList
	 * a prida ju do reaveledCardList
	 */
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

	/**
	 * Prepocitava vysku balicka vzhladom na pocet kariet
	 * v oboch listoch
	 */
	private void calculateNewHeight()
	{
		int sizeOfLists = this.unReaveledCardList.size() + this.reaveledCardList.size();
		this.height = sizeOfLists > 1 ? (sizeOfLists - 1) * Y_CARD_SHIFT + this.defaultHeight : this.defaultHeight;
	}
}
