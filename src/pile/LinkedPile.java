package src.pile;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

import src.card.*;
import src.PlayGround;


public class LinkedPile extends Pile
{
	private int Y_CARD_SHIFT;
	private ArrayList<Card> faceDownCardList;
	private ArrayList<Card> faceUpCardList;

	// Obsahuje zakladnu vysku Pile-u
	private static int defaultHeight;

	public LinkedPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.defaultHeight = height;
		this.faceDownCardList = new ArrayList<Card>();
		this.faceUpCardList = new ArrayList<Card>();
		this.Y_CARD_SHIFT = this.pg.getCardShift();
	}

	@Override
	public void update() {}

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

	@Override
	public boolean insertCard(ListOfCards inputList)
	{
		if (inputList == null || inputList.isEmpty())
		{
			return false;
		}

		if (!checkCorrectCard(inputList))
		{
			return false;
		}

		int counter = 0;
		for (Card c : inputList.getList())
		{
			c.setDefaultPosition(
				this.xPosition,
				this.yPosition + (this.faceDownCardList.size() + this.faceUpCardList.size()) * Y_CARD_SHIFT
				);
			this.faceUpCardList.add(c);
		}

		this.calculateNewHeight();
		return true;
	}

	@Override
	public void removeCard(ListOfCards inputList)
	{
		int numberOfCardsToRemove = inputList.size();
		for (int i = 0; i < numberOfCardsToRemove; ++i)
		{
			int lastCardIndex = this.faceUpCardList.size() - 1;
			this.faceUpCardList.remove(lastCardIndex);
		}

		this.calculateNewHeight();
	}

	@Override
	public void returnListOfCardsToPile(ListOfCards inputList, boolean action)
	{
		if (inputList == null)
		{
			return;
		}

		if (action && !this.faceUpCardList.isEmpty())
		{
			int lastCardIndex = this.faceUpCardList.size() - 1;
			Card tempCard = this.faceUpCardList.get(lastCardIndex);

			this.faceUpCardList.remove(lastCardIndex);
			this.faceDownCardList.add(tempCard);

			tempCard.faceDown();
		}

		for (Card c : inputList.getList())
		{
			c.setDefaultPosition(
				this.xPosition,
				this.yPosition + (this.faceDownCardList.size() + this.faceUpCardList.size()) * Y_CARD_SHIFT
				);
			this.faceUpCardList.add(c);
		}

		this.calculateNewHeight();
	}

	/**
	 * Otoci vrchnu kartu z faceDownCardList
	 * a prida ju do faceUpCardList
	 */
	@Override
	public boolean actionEnded()
	{
		if (faceDownCardList.isEmpty() || faceUpCardList.size() >= 1)
		{
			return false;
		}

		// Reavel top card
		int lastCardIndex = this.faceDownCardList.size() - 1;
		Card tempCard = this.faceDownCardList.get(lastCardIndex);

		this.faceDownCardList.remove(lastCardIndex);
		this.faceUpCardList.add(tempCard);

		tempCard.faceUp();

		return true;
	}

	private boolean checkCorrectCard(ListOfCards inputList)
	{
		// Kontrola spravneho typu kariet
		if (!this.isEmpty())
		{
			if (!this.faceUpCardList.isEmpty())
			{
				Card tempCard = inputList.getFirstCard();
				Card topCard = this.faceUpCardList.get(this.faceUpCardList.size() - 1);

				// Kontrola farby
				if (topCard.colorEqual(tempCard))
				{
					return false;
				}

				// Kontrola hodnoty, rozdiel musi byt 1
				if (topCard.getValue() - tempCard.getValue() != 1)
				{
					return false;
				}
			}
			
		}
		// Prva karta musi byt King
		else
		{
			if (inputList.getFirstCard().getValue() != 13)
			{
				return false;
			}
		}

		return true;
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

	/**
	 * Prepocitava vysku balicka vzhladom na pocet kariet
	 * v oboch listoch
	 */
	private void calculateNewHeight()
	{
		int sizeOfLists = this.faceDownCardList.size() + this.faceUpCardList.size();
		this.height = sizeOfLists > 1 ? (sizeOfLists - 1) * Y_CARD_SHIFT + this.defaultHeight : this.defaultHeight;
	}

	/**
	 * Vrati true ak obydva zoznamy su prazdne
	 */
	private boolean isEmpty()
	{
		return this.faceDownCardList.isEmpty() && this.faceUpCardList.isEmpty();
	}

	/**
	 * Metoda vrati null ak sa karta neda pridat do balicku,
	 * alebo kartu z vrchu balicka ak sa da
	 */
	public boolean canAddCard(Card inputCard)
	{
		return checkCorrectCard(new ListOfCards(null, inputCard, 0));
	}

	/**
	 * Funkcia vrati poslednu(vrchnu) kartu z faceUpCardList
	 * Karta sa zo zoznamu nemaze
	 */
	public Card getLastFaceUpCard()
	{
		if (!this.faceUpCardList.isEmpty())
		{
			return this.faceUpCardList.get(this.faceUpCardList.size() - 1);
		}

		return null;
	}

	public int getFaceDownCardListSize()
	{
		return this.faceDownCardList.size();
	}

	/**
	 * Vrati kartu ktora je pod kartou v argumente funkcie
	 */
	public Card getUnderCard(Card inputCard)
	{
		int underCardIndex = -1;
		for (Card forCard : this.faceUpCardList)
		{
			if (forCard == inputCard)
			{
				//underCardIndex--;
				break;
			}

			underCardIndex++;
		}

		System.out.println("__UNDER: " + underCardIndex);

		return underCardIndex < 0 ? null : this.faceUpCardList.get(underCardIndex);
	}

	public ArrayList<Card> getFaceUpCardList()
	{
		return this.faceUpCardList;
	}
}
