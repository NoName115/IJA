package src;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

import src.pile.*;
import src.card.*;


public class PlayGround
{
	// index 0 - 1 Game mod
	// index 1 - 4 Game mod
	private static final int NUMBER_OF_PILES = 13;
	private static final int NUMBER_OF_LINKED_PILES = 7;
	private static final int[] PANDING = new int[] { 30, 15 };
	private static final int[] Y_CARD_SHIFT = new int[] { 20, 20 };
	private static final int[] CARD_WIDTH = new int[] { 140, 70 };
	private static final int[] CARD_HEIGHT = new int[] { 200, 100 };

	private int gameMod;

	private int width;
	private int height;
	private int xStartPosition;
	private int yStartPosition;

	private boolean firstUpdate = false;

	// Aktualne selecnuta karta
	private ListOfCards actualList;
	private Pile actualPile;

	// Vsetky decky potrebne pre hranie
	private DeckPile deckPile;
	private DrawPile drawPile;
	private DrawHelpPile drawHelpPile;
	private ArrayList<DiscardPile> discardPiles;	// 8 decks
	private ArrayList<LinkedPile> linkedPiles;		// 4 decks

	private Pile[] allPiles;

	public PlayGround(int xPos, int yPos, int width, int height, int gameMod)
	{
		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.gameMod = gameMod;
		this.actualList = null;
		this.actualPile = null;

		// Decks
		this.allPiles = new Pile[NUMBER_OF_PILES];	// 1 + 1 + NUMBER_OF_LINKED_PILES + 4
		this.deckPile = new DeckPile(0, 0, 0, 0, this);
		this.drawHelpPile = new DrawHelpPile(
			this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * 5,
			this.yStartPosition + PANDING[this.gameMod],
			CARD_WIDTH[this.gameMod],
			CARD_HEIGHT[this.gameMod],
			this
			);
		this.drawPile = new DrawPile(
			this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * 6,
			this.yStartPosition + PANDING[this.gameMod],
			CARD_WIDTH[this.gameMod],
			CARD_HEIGHT[this.gameMod],
			this,
			this.drawHelpPile
			);
		this.discardPiles = new ArrayList<DiscardPile>();
		this.linkedPiles = new ArrayList<LinkedPile>();

		this.allPiles[0] = this.drawPile;
		this.allPiles[NUMBER_OF_PILES - 1] = this.drawHelpPile;
		for (int i = 0; i < NUMBER_OF_LINKED_PILES; ++i)
		{
			this.linkedPiles.add(new LinkedPile(
				this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * i,
				this.yStartPosition + PANDING[this.gameMod] * 2 + CARD_HEIGHT[this.gameMod],
				CARD_WIDTH[this.gameMod],
				CARD_HEIGHT[this.gameMod],
				this
				)
			);
			this.allPiles[i + 1] = this.linkedPiles.get(i);
			if (i % 2 == 0)
			{
				this.discardPiles.add(new DiscardPile(
					this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * (i - i / 2),
					this.yStartPosition + PANDING[this.gameMod],
					CARD_WIDTH[this.gameMod],
					CARD_HEIGHT[this.gameMod],
					this
					)
				);
				this.allPiles[NUMBER_OF_LINKED_PILES + 1 + (i - i / 2)] = this.discardPiles.get(i - i / 2);
			}
		}

		this.fillDecks();
		this.firstUpdate = true;
	}

	public void changeGameMod(int xPos, int yPos, int width, int height, int gameMod)
	{
		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.gameMod = gameMod;

		// Set new resolution to all piles
		this.allPiles[0].setNewResolution(
			this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * 6,
			this.yStartPosition + PANDING[this.gameMod],
			CARD_WIDTH[this.gameMod],
			CARD_HEIGHT[this.gameMod]
			);
		this.allPiles[NUMBER_OF_PILES - 1].setNewResolution(
			this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * 5,
			this.yStartPosition + PANDING[this.gameMod],
			CARD_WIDTH[this.gameMod],
			CARD_HEIGHT[this.gameMod]
			);
		for (int i = 0; i < NUMBER_OF_LINKED_PILES; ++i)
		{
			this.allPiles[i + 1].setNewResolution(
				this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * i,
				this.yStartPosition + PANDING[this.gameMod] * 2 + CARD_HEIGHT[this.gameMod],
				CARD_WIDTH[this.gameMod],
				CARD_HEIGHT[this.gameMod]
				);
			if (i % 2 == 0)
			{
				this.allPiles[NUMBER_OF_LINKED_PILES + 1 + (i - i / 2)].setNewResolution(
					this.xStartPosition + PANDING[this.gameMod] + (CARD_WIDTH[this.gameMod] + PANDING[this.gameMod]) * (i - i / 2),
					this.yStartPosition + PANDING[this.gameMod],
					CARD_WIDTH[this.gameMod],
					CARD_HEIGHT[this.gameMod]
					);
			}
		}

		for (int i = 0; i < NUMBER_OF_PILES; ++i)
		{
			this.allPiles[i].setNewDefaultPosition();
		}
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
		g.clearRect(this.xStartPosition, this.yStartPosition, this.width, this.height);

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

			if (this.actualList != null)
			{
				this.actualList.render(g);
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

	// Zachytenie karty
	public void mousePressed(int ix, int iy)
	{
		for (int i = 0; i < NUMBER_OF_PILES; ++i)
		{
			if (this.allPiles[i].isInPile(ix, iy))
			{
				this.actualPile = this.allPiles[i];
				this.actualList = this.allPiles[i].selectPile(ix, iy);

				if (this.actualList != null)
				{
					//this.actualList.printDebug();
					this.actualList.setIsDragged(true, ix, iy);
				}
			}
		}
	}

	// Pustenie karty
	public void mouseReleased(int ix, int iy)
	{
		if (this.actualList != null)
		{
			for (int i = 0; i < NUMBER_OF_PILES; ++i)
			{
				if (this.allPiles[i].isInPile(ix, iy))
				{
					boolean wasInserted = this.allPiles[i].insertCard(this.actualList);
					if (!wasInserted)
					{
						// Urobi return kariet
						break;
					}

					this.actualPile.actionEnded();
					this.actualList.setIsDragged(false, ix, iy);

					this.actualPile = null;
					this.actualList = null;
					return;
				}
			}

			this.actualPile.returnCard(this.actualList);
			this.actualList.setIsDragged(false, ix, iy);
		}

		this.actualPile = null;
		this.actualList = null;
	}

	// Pohyb karty
	public void mouseDragged(int ix, int iy)
	{
		if (this.actualList != null)
		{
			this.actualList.setActualPosition(ix, iy);
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

	public int getCardWidth() { return CARD_WIDTH[this.gameMod]; }
	public int getCardHeight() { return CARD_HEIGHT[this.gameMod]; }

	public int getCardShift() { return Y_CARD_SHIFT[this.gameMod]; }

}
