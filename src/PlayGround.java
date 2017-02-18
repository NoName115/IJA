package src;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;


public class PlayGround
{
	private static final int NUMBER_OF_PILES = 12;

	private int width;
	private int height;
	private int xStartPosition;
	private int yStartPosition;

	private boolean firstUpdate = false;

	// Aktualne selecnuta karta
	private Card actualCard;
	private Pile actualPile;

	// Vsetky decky potrebne pre hranie
	private DeckPile deckPile;
	private DrawPile drawPile;
	private ArrayList<DiscardPile> discardPiles;	// 8 decks
	private ArrayList<LinkedPile> linkedPiles;		// 4 decks

	private Pile[] allPiles;

	public PlayGround(int xPos, int yPos, int width, int height)
	{
		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.actualCard = null;
		this.actualPile = null;

		// Decks
		this.allPiles = new Pile[NUMBER_OF_PILES];	// 1 + 7 + 4
		this.deckPile = new DeckPile();
		this.drawPile = new DrawPile(10 + (80 + 70) * 6, 10, 80, 120);
		this.discardPiles = new ArrayList<DiscardPile>();
		this.linkedPiles = new ArrayList<LinkedPile>();

		this.allPiles[0] = drawPile;
		for (int i = 0; i < 7; ++i)
		{
			this.linkedPiles.add(new LinkedPile(10 + (80 + 70) * i, 140, 80, 120));
			this.allPiles[i + 1] = this.linkedPiles.get(i);
			if (i % 2 == 0)
			{
				this.discardPiles.add(new DiscardPile(10 + (80 + 70) * (i - i / 2), 10, 80, 120));
				this.allPiles[8 + (i - i / 2)] = this.discardPiles.get(i - i / 2);
			}
		}

		this.fillDecks();
		this.firstUpdate = true;
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
		if (this.firstUpdate)
		{
			// Reavel first cards
			for (LinkedPile lp : this.linkedPiles)
			{
				lp.actionEnded();
			}
			this.firstUpdate = false;
		}
	}

	// Render hry
	public void render(Graphics g)
	{
		g.clearRect(this.xStartPosition, this.yStartPosition, this.width, this.width);

		try
		{
			for (LinkedPile lp : this.linkedPiles)
			{
				lp.render(g);
			}

			for (DiscardPile dp : this.discardPiles)
			{
				dp.render(g);
			}

			drawPile.render(g);

			if (this.actualCard != null)
			{
				this.actualCard.render(g);
			}
		}
		catch (ConcurrentModificationException e)
		{
			System.out.println("Ocakavana chyba: " + e);
		}

		
	}

	public boolean checkSection(int ix, int iy)
	{
		if (this.xStartPosition <= ix && this.xStartPosition + this.width >= ix)
		{
			if (this.yStartPosition <= iy && this.yStartPosition + this.height >= iy)
			{
				return true;
			}
		}

		return false;
	}

	public void mousePressed(int ix, int iy)
	{
		for (int i = 0; i < NUMBER_OF_PILES; ++i)
		{
			if (this.allPiles[i].isInPile(ix, iy))
			{
				this.actualPile = this.allPiles[i];
				this.actualCard = this.allPiles[i].selectPile(ix, iy);

				if (this.actualCard != null)
				{
					this.actualCard.printDebug();
					this.actualCard.setIsDragged(true);
				}
			}
		}
	}

	public void mouseReleased(int ix, int iy)
	{
		if (this.actualCard != null)
		{
			for (int i = 0; i < NUMBER_OF_PILES; ++i)
			{
				if (this.allPiles[i].isInPile(ix, iy))
				{
					boolean wasInserted = this.allPiles[i].insertCard(this.actualCard);
					this.actualPile.actionEnded();
					this.actualCard.setIsDragged(false);

					this.actualPile = null;
					this.actualCard = null;
					return;
				}
			}

			this.actualPile.returnCard(this.actualCard);
			this.actualCard.setIsDragged(false);
		}

		this.actualPile = null;
		this.actualCard = null;
	}

	public void mouseDragged(int ix, int iy)
	{
		if (this.actualCard != null)
		{
			this.actualCard.setActualPosition(ix, iy);
		}
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
