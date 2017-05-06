package solitaire.pile;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

import solitaire.card.*;
import solitaire.PlayGround;


public class LinkedPile_Client extends Pile_Client
{
	private int Y_CARD_SHIFT;
	private ArrayList<Card> faceDownCardList;
	private ArrayList<Card> faceUpCardList;

	// Obsahuje zakladnu vysku Pile-u
	private static int defaultHeight;

	public LinkedPile_Client(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.defaultHeight = height;
		this.faceDownCardList = new ArrayList<Card>();
		this.faceUpCardList = new ArrayList<Card>();
		this.Y_CARD_SHIFT = this.pg.getCardShift();
	}

	/**
	 * Najprv sa renderuje faceDownCardList a tak reaveled
	 */
	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.defaultHeight);

		for (Card c : this.faceDownCardList)
		{
			c.render(g);
		}

		for (Card c : this.faceUpCardList)
		{
			c.render(g);
		}
	}

	@Override
	public void addCard(Card inputCard)
	{
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition + Y_CARD_SHIFT * this.faceDownCardList.size()
			);
		this.faceDownCardList.add(inputCard);
		this.calculateNewHeight();
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
		for (int i = 0; i < this.faceDownCardList.size(); ++i)
		{
			this.faceDownCardList.get(i).setDefaultPosition(
				this.xPosition,
				this.yPosition + i * this.Y_CARD_SHIFT
				);
		}

		for (int i = 0; i < this.faceUpCardList.size(); ++i)
		{
			this.faceUpCardList.get(i).setDefaultPosition(
				this.xPosition,
				this.yPosition + (i + this.faceDownCardList.size()) * this.Y_CARD_SHIFT
				);
		}
	}

	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		if (this.faceUpCardList.isEmpty())
		{
			return null;
		}

		// Nachadzam sa v casti odhalenych kariet
		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			// Horny a Dolny bod zoznamu odhalenych kariet
			int startYPos = this.faceUpCardList.get(0).getYDefaultPosition();
			int endYPos = startYPos + (this.faceUpCardList.size() - 1) * Y_CARD_SHIFT + this.defaultHeight;

			ListOfCards tempList = null;
			if (startYPos <= iy && endYPos >= iy)
			{
				// Celkova vyska odhaleneho balicka kariet
				int summaryYShift = (this.faceUpCardList.size() - 1 + this.faceDownCardList.size()) * Y_CARD_SHIFT + this.yPosition;

				// Vrati zoznam kariet do index po koniec zoznamu
				if (iy < summaryYShift)
				{
					int index = (iy - this.yPosition - this.faceDownCardList.size() * Y_CARD_SHIFT) / Y_CARD_SHIFT;
					int listSize = this.faceUpCardList.size();

					ArrayList<Card> outputCardList = new ArrayList<Card>();

					for (int i = index; i < listSize; ++i)
					{
						outputCardList.add(this.faceUpCardList.get(index));
						this.faceUpCardList.remove(index);
					}

					tempList = new ListOfCards(outputCardList, null, Y_CARD_SHIFT);
				}
				// Vrati len 1, poslednu kartu (vrchnu kartu)
				else if (iy >= summaryYShift && iy <= (summaryYShift + this.defaultHeight))
				{
					int lastCardIndex = this.faceUpCardList.size() - 1;
					tempList = new ListOfCards(null, this.faceUpCardList.get(lastCardIndex), Y_CARD_SHIFT);
					this.faceUpCardList.remove(lastCardIndex);
				}

				this.calculateNewHeight();
				return tempList;
			}
		}

		return null;
	}

	/**
	 * Otoci vrchnu kartu z faceDownCardList
	 * a prida ju do faceUpCardList
	 */
	public boolean reavelTopCard()
	{
		if (this.faceDownCardList.isEmpty() || this.faceUpCardList.size() >= 1)
		{
			return false;
		}

		// Reavel top solitaire.card
		int lastCardIndex = this.faceDownCardList.size() - 1;
		Card tempCard = this.faceDownCardList.get(lastCardIndex);

		this.faceDownCardList.remove(lastCardIndex);
		this.faceUpCardList.add(tempCard);

		tempCard.faceUp();

		return true;
	}

	/**
	 * Prepocitava vysku balicka vzhladom na pocet kariet
	 * v oboch listoch
	 */
	private void calculateNewHeight()
	{
		int sizeOfLists = this.faceDownCardList.size() + this.faceUpCardList.size();
		this.height = sizeOfLists > 1 ? (sizeOfLists - 1) * Y_CARD_SHIFT + this.defaultHeight : this.defaultHeight;
	}
}
