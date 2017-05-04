package solitaire.pile;

import java.awt.Graphics;
import java.awt.Color;

import solitaire.card.*;
import solitaire.PlayGround;


public class DiscardPile extends Pile
{
	public DiscardPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g)
	{
		// Render spodku balicka
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
		g.drawString("A", this.xPosition + this.width / 2 - 4, this.yPosition + this.height / 2 + 5);

		for (Card c : this.cardList)
		{
			c.render(g);
		}
	}

	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		if (cardList.isEmpty())
		{
			return null;
		}

		int lastIndex = this.cardList.size() - 1;
		Card tempCard = cardList.get(lastIndex);
		this.cardList.remove(lastIndex);

		return new ListOfCards(null, tempCard, this.pg.getCardShift());
	}

	@Override
	public boolean insertCard(ListOfCards inputList)
	{
		if (inputList == null || inputList.isEmpty() || inputList.size() > 1)
		{
			return false;
		}

		if (!checkCorrectCard(inputList))
		{
			return false;
		}

		for (Card c : inputList.getList())
		{
			c.setDefaultPosition(
				this.xPosition,
				this.yPosition
				);
			this.cardList.add(c);
		}

		return true;
	}

	@Override
	public void removeCard(ListOfCards inputList)
	{
		this.cardList.remove(this.cardList.size() - 1);
	}

	@Override
	public void returnListOfCardsToPile(ListOfCards inputList, boolean action)
	{
		if (inputList != null)
		{
			for (Card c : inputList.getList())
			{
				c.setDefaultPosition(
					this.xPosition,
					this.yPosition
					);
				this.cardList.add(c);
			}
		}
	}

	private boolean checkCorrectCard(ListOfCards inputList)
	{
		// Kontrola spravneho typu kariet
		if (!this.cardList.isEmpty())
		{
			Card tempCard = inputList.getFirstCard();

			// Rovnaka farba karty
			if (!this.cardList.get(0).typeEqual(tempCard))
			{
				return false;
			}

			// Hodnota karty o 1 vyssia
			if (tempCard.getValue() - this.cardList.get(this.cardList.size() - 1).getValue() != 1)
			{
				return false;
			}
		}
		// Prva karta musi byt Eso(A)
		else
		{
			if (inputList.getFirstCard().getValue() != 1)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Metoda vrati null ak sa karta neda pridat do balicku,
	 * alebo kartu z vrchu balicka ak sa da
	 */
	public boolean canAddCard(Card inputCard)
	{
		ListOfCards inputList = new ListOfCards(null, inputCard, 0);

		if (checkCorrectCard(inputList))
		{
			return true;
		}

		return false;
	}

	/*
	 * Kontroluje ci solitaire.pile obsahuje vsetky potrebne karty
	 * pre ukoncenie hry
	 * Staci skontrolovat pocet kariet
	 */
	public boolean containAllCards()
	{
		return (this.cardList.size() == 13) ? true : false;
	}
}