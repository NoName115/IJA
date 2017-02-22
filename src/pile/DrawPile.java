package src.pile;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;

import src.card.*;
import src.PlayGround;


public class DrawPile extends Pile
{
	// Pile kde sa nachadza list odhalenych kariet
	private DrawHelpPile helpPile;

	public DrawPile(int xPos, int yPos, int width, int height, PlayGround pg, DrawHelpPile helpPile)
	{
		super(xPos, yPos, width, height, pg);

		this.helpPile = helpPile;
	}

	@Override
	public void update() {}

	/**
	 * Render balicku kariet a helpPile objektu
	 */
	@Override
	public void render(Graphics g)
	{
		// Zakladne pozadie spodku balicka
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
		g.drawOval(this.xPosition + this.width / 4, this.yPosition + this.height / 2 - this.width / 4,
					this.width / 2, this.width / 2);

		// Render kariet
		for (Card c : this.cardList)
		{
			c.render(g);
		}

		// Render helpPile-u
		if (this.helpPile != null)
		{
			this.helpPile.render(g);
		}
	}

	/**
	 * Vzdy vracia null
	 * Prehadzuje karty medzi totou Pile-ou a helpPile-ou
	 */
	@Override
	public ListOfCards selectPile(int ix, int iy)
	{
		// Dotahovaci balicek je prazdny a treba vsetko
		// z helpPile dat do drawPile ale revezne
		if (this.cardList.isEmpty())
		{
			Card inputCard;
			while((inputCard = this.helpPile.getLastCard()) != null)
			{
				this.cardList.add(inputCard);

				inputCard.faceDown();
				inputCard.setDefaultPosition(this.xPosition, this.yPosition);
			}

			return null;
		}

		// Prehodi vrchnu kartu z drawPile do helpPile
		int lastIndex = this.cardList.size() - 1;
		Card tempCard = this.cardList.get(lastIndex);
		this.cardList.remove(lastIndex);
		this.helpPile.addCard(tempCard);

		tempCard.faceUp();
		return null;
	}
}