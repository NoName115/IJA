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
	public CardOrList selectPile(int ix, int iy)
	{
		if (this.reaveledCardList.isEmpty())
		{
			return null;
		}

		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			int startYPos = this.reaveledCardList.get(0).getYDefaultPosition();
			int endYPos = startYPos + (this.reaveledCardList.size() - 1) * Y_CARD_SHIFT + this.defaultHeight;

			CardOrList tempCardOrList = null;

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

					tempCardOrList = new CardOrList(null, outputCardList);
				}
				// Vrati len 1, poslednu kartu
				else if (iy >= summaryYShift && iy <= (summaryYShift + this.defaultHeight))
				{
					int lastCardIndex = this.reaveledCardList.size() - 1;
					tempCardOrList = new CardOrList(this.reaveledCardList.get(lastCardIndex), null);
					this.reaveledCardList.remove(lastCardIndex);
				}

				this.calculateNewHeight();
				return tempCardOrList;
			}
		}

		return null;
	}

	// insertCardOrList
	public boolean insertCard(CardOrList inputCardOrList)
	{
		if (inputCardOrList == null)
		{
			return false;
		}

		Card inputCard;
		if ((inputCard = inputCardOrList.getCard()) != null)
		{
			inputCard.setDefaultPosition(
				this.xPosition,
				this.yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size())
				);
			this.reaveledCardList.add(inputCard);
		}

		ArrayList<Card> listOfCards;
		if ((listOfCards = inputCardOrList.getList()) != null)
		{
			for (int i = 0; i < listOfCards.size(); ++i)
			{
				listOfCards.get(i).setDefaultPosition(
					this.xPosition,
					this.yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size()) 
					);
				this.reaveledCardList.add(listOfCards.get(i));
			}
		}

		this.calculateNewHeight();
		return true;
	}

	// returnCardOrListToPile
	public void returnCard(CardOrList inputCardOrList)
	{
		if (inputCardOrList != null)
		{
			Card inputCard;
			if ((inputCard = inputCardOrList.getCard()) != null)
			{
				inputCard.setDefaultPosition(
					this.xPosition,
					this.yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size())
					);
				this.reaveledCardList.add(inputCard);
			}

			ArrayList<Card> listOfCards;
			if ((listOfCards = inputCardOrList.getList()) != null)
			{
				for (int i = 0; i < listOfCards.size(); ++i)
				{
					listOfCards.get(i).setDefaultPosition(
						this.xPosition,
						this.yPosition + Y_CARD_SHIFT * (this.unReaveledCardList.size() + this.reaveledCardList.size()) 
						);
					this.reaveledCardList.add(listOfCards.get(i));
				}
			}

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
