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
				for (int i = 0; i <= (endYPos - startYPos - this.defaultHeight) / Y_CARD_SHIFT; ++i)
				{
					System.out.println("I: " + i);

					// Karty kde vidno len vrch
					if (this.reaveledCardList.size() - 1 != i)
					{
						System.out.println("_1_");

						if ((startYPos + Y_CARD_SHIFT * i) <= iy && (startYPos + Y_CARD_SHIFT * (i + 1)) > iy)
						{
							System.out.println("TU SOM: " + i + " - " + this.reaveledCardList.size());

							// Vrati cely zoznam kariet
							int sizeOfReaveledList = this.reaveledCardList.size();
							ArrayList<Card> outputCardList = new ArrayList<Card>();

							for (int j = i; j < sizeOfReaveledList; ++j)
							{
								outputCardList.add(this.reaveledCardList.get(i));
								this.reaveledCardList.remove(i);
							}

							tempCardOrList =  new CardOrList(null, outputCardList);
						}
					}
					// Vrchna karta z reaveledCardList
					else
					{
						System.out.println("_2_");

						if ((startYPos + Y_CARD_SHIFT * i) <= iy && (startYPos + Y_CARD_SHIFT * i + this.defaultHeight) >= iy)
						{
							System.out.println("_22_");

							// Vrati len samotnu kartu
							tempCardOrList = new CardOrList(this.reaveledCardList.get(i), null);
							this.reaveledCardList.remove(i);
						}
					}

					this.calculateNewHeight();
					return tempCardOrList;
				}
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
