package src.pile;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

import src.card.*;
import src.PlayGround;


public class DiscardPile extends Pile
{
	private ArrayList<Card> cardList;

	public DiscardPile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		super(xPos, yPos, width, height, pg);

		this.cardList = new ArrayList<Card>();
	}

	@Override
	public void setNewDefaultPosition()
	{
		for (Card c : this.cardList)
		{
			c.setDefaultPosition(this.xPosition, this.yPosition);
		}
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g)
	{
		// Render spodku balicka
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
		g.drawString("A", this.xPosition + this.width / 2, this.yPosition + this.height / 2);

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
	public void returnListOfCardsToPile(ListOfCards inputList)
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

	public void addCard(Card card) {}
}