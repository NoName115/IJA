package solitaire.pile.client;

import java.awt.Graphics;
import java.awt.Color;

import solitaire.card.*;
import solitaire.PlayGround;


public class DiscardPile_Client extends Pile_Client
{
	public DiscardPile_Client(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);
	}

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
}