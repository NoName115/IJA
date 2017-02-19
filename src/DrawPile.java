package src;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;


public class DrawPile extends Pile
{
	private ArrayList<Card> cardList;
	private DrawHelpPile helpPile;

	public DrawPile(int xPos, int yPos, int width, int height, DrawHelpPile helpPile)
	{
		super(xPos, yPos, width, height);

		this.helpPile = helpPile;
		this.cardList = new ArrayList<Card>();
	}

	public void update()
	{
		return;
	}

	// Render 2 vrchnych kariet z oboch ArrayList-ov
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
		g.drawOval(this.xPosition + this.width / 4, this.yPosition + this.height / 2 - this.width / 4,
					this.width / 2, this.width / 2);

		for (Card c : this.cardList)
		{
			c.render(g);
		}

		if (this.helpPile != null)
		{
			this.helpPile.render(g);
		}
	}

	public ListOfCards selectPile(int ix, int iy)
	{
		// Dotahovaci balicek je prazdny a treba vsetko
		// z DrawHelpPile dat do DrawPile ale revezne
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

		// Prehodi vrchnu kartu z DrawPile do DrawHelpPile
		int lastIndex = this.cardList.size() - 1;
		Card tempCard = this.cardList.get(lastIndex);
		this.cardList.remove(lastIndex);
		this.helpPile.addCard(tempCard);

		tempCard.faceUp();
		return null;
	}

	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
		inputCard.setDefaultPosition(this.xPosition, this.yPosition);
	}
}