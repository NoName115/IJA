package solitaire.pile;

import java.awt.Graphics;

import java.util.ArrayList;

import solitaire.card.*;
import solitaire.PlayGround;


public class DrawHelpPile_Client extends Pile_Client
{
	public DrawHelpPile_Client(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);
	}

	@Override
	public void render(Graphics g)
	{
		for (Card c : this.cardList)
		{
			c.render(g);
		}
	}

	/**
	 * Vrati jednu vrchnu(poslednu) kartu
	 */
	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		if (this.cardList.isEmpty())
		{
			return null;
		}

		int lastIndex = this.cardList.size() - 1;
		Card tempCard = this.cardList.get(lastIndex);
		this.cardList.remove(lastIndex);

		return new ListOfCards(null, tempCard, this.pg.getCardShift());
	}
}