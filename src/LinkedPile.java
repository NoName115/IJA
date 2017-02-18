package src;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;


public class LinkedPile extends Pile
{
	// TODO
	// meni sa HEIGHT ked sa prida/odoberie karta
	// kvoli funkcii isInPile

	private static final int Y_CARD_SHIFT = 30;
	private ArrayList<Card> unReaveledCardList;
	private ArrayList<Card> reaveledCardList;

	public LinkedPile(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;

		this.unReaveledCardList = new ArrayList<Card>();
		this.reaveledCardList = new ArrayList<Card>();
	}

	// Ked je reaveledCardList prazdny,
	// tak prida kartu z unReaveledCardList do reaveledCardList
	public void update()
	{
		if (unReaveledCardList.isEmpty())
		{
			return;
		}

		if (reaveledCardList.size() >= 1)
		{
			return;
		}

		int lastCardIndex = unReaveledCardList.size() - 1;
		Card tempCard = unReaveledCardList.get(lastCardIndex);
		unReaveledCardList.remove(lastCardIndex);
		reaveledCardList.add(tempCard);
		tempCard.faceUp();
	}

	// Render vsetkych kariet
	// Najprv sa renderuje unReaveledCardList
	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xPosition, this.yPosition + Y_CARD_SHIFT, this.width, this.height);

		for (Card c : this.unReaveledCardList)
		{
			c.render(g);
		}

		for (Card c : this.reaveledCardList)
		{
			c.render(g);
		}
	}

	// TODO
	// Vytvorit objekt ktory obsahuje Card a ArrayList
	public Card selectPile(int ix, int iy)
	{
		return null;
	}

	public boolean insertCard(Card inputCard)
	{
		reaveledCardList.add(inputCard);
		inputCard.setDefaultPosition(
			xPosition,
			yPosition + Y_CARD_SHIFT * (unReaveledCardList.size() + reaveledCardList.size())
			);
		return true;
	}

	public void addCard(Card inputCard)
	{
		unReaveledCardList.add(inputCard);
		inputCard.setDefaultPosition(
			this.xPosition,
			this.yPosition + Y_CARD_SHIFT * unReaveledCardList.size()
			);
	}
}
