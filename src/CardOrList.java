package src;

import java.awt.Graphics;
import java.util.ArrayList;


public class CardOrList
{
	private Card card;
	private ArrayList<Card> listOfCards;

	public CardOrList(Card inputCard, ArrayList<Card> inputCardList)
	{
		this.card = inputCard;
		this.listOfCards = inputCardList;
	}

	public void render(Graphics g)
	{
		if (this.listOfCards != null)
		{
			for (Card c : this.listOfCards)
			{
				c.render(g);
			}
		}

		if (this.card != null)
		{
			this.card.render(g);
		}
	}
}