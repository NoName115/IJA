package src;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;


public class DiscardPile extends Pile
{
	private ArrayList<Card> cardList;

	public DiscardPile(int xPos, int yPos, int width, int height)
	{
		super(xPos, yPos, width, height);

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

	public ListOfCards selectPile(int ix, int iy)
	{
		if (cardList.isEmpty())
		{
			return null;
		}

		int lastIndex = this.cardList.size() - 1;
		Card tempCard = cardList.get(lastIndex);
		this.cardList.remove(lastIndex);

		return new ListOfCards(null, tempCard);
	}

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

	public void returnCard(ListOfCards inputList)
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

	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
	}
}