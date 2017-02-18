package src;

import java.awt.Graphics;
import java.util.ArrayList;


public class PlayGround
{
	private int width;
	private int height;
	private int xStartPosition;
	private int yStartPosition;

	// Aktualne selecnuta karta
	private Card actualCard;

	// Vsetky decky potrebne pre hranie
	private DeckPile deckPile;
	private DrawPile drawPile;
	private ArrayList<DiscardPile> discardPiles;	// 8 decks
	private ArrayList<LinkedPile> linkedPiles;		// 4 decks

	public PlayGround(int xPos, int yPos, int width, int height)
	{
		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.actualCard = null;

		// Decks
		this.deckPile = new DeckPile();
		this.drawPile = new DrawPile(10 + (80 + 70) * 6, 10, 80, 120);
		this.discardPiles = new ArrayList<DiscardPile>();
		this.linkedPiles = new ArrayList<LinkedPile>();

		for (int i = 0; i < 7; ++i)
		{
			this.linkedPiles.add(new LinkedPile(10 + (80 + 70) * i, 140, 80, 120));
			if (i % 2 == 0)
			{
				this.discardPiles.add(new DiscardPile(10 + (80 + 70) * (i - i / 2), 10, 80, 120));
			}
		}

		this.fillDecks();
	}

	private void fillDecks()
	{
		for (int i = 0; i < 7; ++i)
		{
			for (int j = 0; j < i + 1; ++j)
			{
				this.linkedPiles.get(i).addCard(this.deckPile.popCard());
			}
		}

		Card deckPileCard = null;
		while ((deckPileCard = this.deckPile.popCard()) != null)
		{
			this.drawPile.addCard(deckPileCard);
		}
	}

	// Update pre logiku hry
	public void update()
	{
		return;
	}

	// Render hry
	public void render(Graphics g)
	{
		for (LinkedPile lp : this.linkedPiles)
		{
			lp.render(g);
		}

		for (DiscardPile dp : this.discardPiles)
		{
			dp.render(g);
		}
	}

	public void mousePressed(int ix, int iy)
	{
		System.out.println("X: " + ix + " Y: " + iy);
	}

	public void mouseReleased(int ix, int iy)
	{
		return;
	}

	public void mouseMoved(int ix, int iy)
	{
		return;
	}

	public int getXStartPosition()
	{
		return xStartPosition;
	}

	public int getYStartPosition()
	{
		return yStartPosition;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

}
