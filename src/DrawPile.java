package src;

import java.awt.Graphics;
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
		return;
	}

	public Card selectPile(int ix, int iy)
	{
		// Dotahovaci balicek je prazdny a treba vsetko
		// z reaveledCardList dat do unReaveledCardList ale reveznute
		if (unReaveledCardList.isEmpty())
		{
			Collections.reverse(reaveledCardList);
			while (!reaveledCardList.isEmpty())
			{
				unReaveledCardList.add(reaveledCardList.get(0));
				reaveledCardList.remove(0);
			}

			return null;
		}

		// Prehodi vrchnu kartu z unReaveledCardList do reaveledCardList
		int indexOfTempCard = unReaveledCardList.size() - 1;
		Card tempCard = unReaveledCardList.get(indexOfTempCard);
		unReaveledCardList.remove(indexOfTempCard);
		reaveledCardList.add(tempCard);

		return null;
	}

	public boolean insertCard(Card inputCard)
	{
		return false;
	}

	public void addCard(Card inputCard)
	{
		this.unReaveledCardList.add(inputCard);
	}
}