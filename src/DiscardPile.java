package src;

import java.awt.Graphics;
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
		return;
	}

	// Render staci len vrchnych 2 kariet
	// lastIndex a lastIndex - 1
	public void render(Graphics g)
	{
		return;
	}

	public Card selectPile(int ix, int iy)
	{
		if (cardList.isEmpty())
		{
			return null;
		}

		return cardList.get(cardList.size() - 1);
	}

	public boolean insertCard(Card inputCard)
	{
		if (inputCard == null)
		{
			return false;
		}

		cardList.add(inputCard);
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition
			);
		return true;
	}

	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
	}
}