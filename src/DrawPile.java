package src;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Collections;


public class DrawPile extends Pile
{
	private ArrayList<Card> unReaveledCardList;
	private ArrayList<Card> reaveledCardList;

	public DrawPile(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;

		this.unReaveledCardList = new ArrayList<Card>();
		this.reaveledCardList = new ArrayList<Card>();
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

		for (Card c : this.unReaveledCardList)
		{
			c.render(g);
		}

		for (Card c : this.reaveledCardList)
		{
			c.render(g);
		}
	}

	public CardOrList selectPile(int ix, int iy)
	{
		// Dotahovaci balicek je prazdny a treba vsetko
		// z reaveledCardList dat do unReaveledCardList ale reveznute
		if (unReaveledCardList.isEmpty())
		{
			Collections.reverse(reaveledCardList);
			while (!reaveledCardList.isEmpty())
			{
				Card tempCard = reaveledCardList.get(0);
				unReaveledCardList.add(tempCard);
				reaveledCardList.remove(0);

				tempCard.faceDown();
				tempCard.setDefaultPosition(this.xPosition, this.yPosition);
			}

			return null;
		}

		// Prehodi vrchnu kartu z unReaveledCardList do reaveledCardList
		int indexOfTempCard = unReaveledCardList.size() - 1;
		Card tempCard = unReaveledCardList.get(indexOfTempCard);
		unReaveledCardList.remove(indexOfTempCard);
		reaveledCardList.add(tempCard);

		tempCard.faceUp();
		tempCard.setDefaultPosition(this.xPosition - 80 - 70, this.yPosition);

		return null;
	}

	public boolean insertCard(Card inputCard)
	{
		return false;
	}

	public void addCard(Card inputCard)
	{
		this.unReaveledCardList.add(inputCard);
		inputCard.setDefaultPosition(this.xPosition, this.yPosition);
	}
}