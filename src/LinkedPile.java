package src;

import java.awt.Graphics;
import java.util.ArrayList;


public class LinkedPile extends Pile
{
	// TODO
	// meni sa HEIGHT ked sa prida/odoberie karta

	private static final int Y_CARD_SHIFT = 10;
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

	public void update()
	{
		return;
	}

	// Render vsetkych kariet
	// Najprv sa renderuje unRevealedCardList
	public void render()
	{
		return;
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
}
