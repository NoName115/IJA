package src;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;


public class DiscardPile extends Pile
{
	private ArrayList<Card> cardList;

	public DiscardPile(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;

		this.cardList = new ArrayList<Card>();
	}

	public void update()
	{
		// NOTHING
	}

	// Render staci len vrchnych 2 kariet
	// lastIndex a lastIndex - 1
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
		g.drawString("A", this.xPosition + this.width / 2, this.yPosition + this.height / 2);

		for (Card c : this.cardList)
		{
			c.render(g);
		}
	}

	public Card selectPile(int ix, int iy)
	{
		if (cardList.isEmpty())
		{
			return null;
		}

		Card tempCard = cardList.get(cardList.size() - 1);
		cardList.remove(cardList.size() - 1);
		return tempCard;
	}

	public boolean insertCard(Card inputCard)
	{
		if (inputCard == null)
		{
			return false;
		}

		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition
			);
		this.cardList.add(inputCard);
		return true;
	}

	public void returnCard(Card inputCard)
	{
		if (inputCard != null)
		{
			inputCard.setDefaultPosition(
				this.xPosition,
				this.yPosition
				);
			this.cardList.add(inputCard);
		}
	}

	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
	}
}