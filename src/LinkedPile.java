package src;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;


public class LinkedPile extends Pile
{
	// TODO
	// meni sa HEIGHT ked sa prida/odoberie karta
	// kvoli funkcii isInPile

	private static final int Y_CARD_SHIFT = 30;
	private ArrayList<Card> unReaveledCardList;
	private ArrayList<Card> reaveledCardList;

	private static int defaultHeight;

	public LinkedPile(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;
		this.defaultHeight = height;

		this.unReaveledCardList = new ArrayList<Card>();
		this.reaveledCardList = new ArrayList<Card>();
	}

	public void update()
	{
		return;
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
	public Card selectPile(int ix, int iy)
	{
		if (this.reaveledCardList.isEmpty())
		{
			return null;
		}

		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			int startYPos = this.reaveledCardList.get(0).getYDefaultPosition();
			int endYPos = startYPos + (this.reaveledCardList.size() - 1) * Y_CARD_SHIFT + this.defaultHeight;

			Card tempCard = null;
			boolean returnCard = false;

			if (startYPos <= iy && endYPos >= iy)
			{
				for (int i = 0; i <= (endYPos - startYPos - this.defaultHeight) / Y_CARD_SHIFT; ++i)
				{
					// Karty kde vidno len vrch
					if (this.reaveledCardList.size() - 1 != i)
					{
						if ((startYPos + Y_CARD_SHIFT * i) <= iy && (startYPos + Y_CARD_SHIFT * (i + 1)) > iy)
						{
							returnCard = true;
						}
					}
					// Vrchna karta z reaveledCardList
					else
					{
						if ((startYPos + Y_CARD_SHIFT * i) <= iy && (startYPos + Y_CARD_SHIFT * i + this.defaultHeight) >= iy)
						{
							returnCard = true;
						}
					}

					if (returnCard)
					{
						tempCard = this.reaveledCardList.get(i);
						this.reaveledCardList.remove(i);
						this.calculateNewHeight();
						return tempCard;
					}
				}
			}
		}

		return null;
	}

	public boolean insertCard(Card inputCard)
	{
		if (inputCard == null)
		{
			return false;
		}

		inputCard.setDefaultPosition(
			xPosition,
			yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size())
			);
		this.reaveledCardList.add(inputCard);
		this.calculateNewHeight();
		return true;
	}

	public void returnCard(Card inputCard)
	{
		if (inputCard != null)
		{
			inputCard.setDefaultPosition(
				this.xPosition,
				this.yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size())
				);
			this.reaveledCardList.add(inputCard);
			this.calculateNewHeight();
		}
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
