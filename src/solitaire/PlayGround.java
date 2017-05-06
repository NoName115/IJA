package solitaire;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

import solitaire.pile.*;
import solitaire.card.*;

/**
 * Object reprezentujuci jednu hraciu plochu
*/
public class PlayGround extends PlayGroundBase
{
	private static final int NUMBER_OF_PILES = 13;
    private static final int NUMBER_OF_LINKED_PILES = 7;

	// index 0 - rozmery pre 1 hru
	// index 1 - rozmery pre 2-4 hry
	private static final int[] PANDING = new int[] { 30, 15 };
	private static final int[] Y_CARD_SHIFT = new int[] { 20, 20 };
	private static final int[] CARD_WIDTH = new int[] { 140, 70 };
	private static final int[] CARD_HEIGHT = new int[] { 200, 100 };
	private static final int[] PANEL_SIZE = new int[] { 80, 40 };

	// Aktualne vybrana karta alebo list kariet
    private ListOfCards actualList;
    // Aktualny solitaire.pile z ktoreho bola karta zobrata
    private Pile actualPile;

	private int gameMod;

	private int width;
	private int height;
	private int xStartPosition;
	private int yStartPosition;

	private boolean firstUpdate = false;

	public PlayGround(int xPos, int yPos, int width, int height, int gameMod)
	{
		super();

		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.gameMod = gameMod;
		this.actualList = null;
		this.actualPile = null;

		this.undoList = new ArrayList<Command>();

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

	/**
	 * Vola sa pri zmene herneho modu z 2 na 1 pocet hier alebo opacne
	 * Vola funkciu setNewResolution pre kazdy Pile
	 */
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
	}

	/**
	 * Naplni kazdy deck potrebnym poctom kariet
	 * Funkcia popCard vracia nahodnu kartu z balicka
	 */
	// TODO
	// Nebude tu tato funkcia, bude pridanie karty zo servera
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

	/**
	 * Update pre logiku hry
	 */
	// TODO
	// Bezi na servery??
	// Pridat funkciu do Interface pre clienta ze actionEnded() - otocit kartu
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

		checkGameEnded();
	}

	/**
	 * Render pre vsetky objekty hry
	 */
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

		if (this.gameEnded)
		{
			g.drawString(
				"Vyhral si !!!",
				this.xStartPosition + this.width / 3,
				this.yStartPosition + this.height / 2
				);
		}
	}

	/**
	 * Volane z triedy GameController
	 * Kontroluje ci uzivatel klikol do spravneho hracieho pola
	 */
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

	/**
	 * Zachytenie karty a solitaire.pile-u
	 */
	public void mousePressed(int ix, int iy)
	{
		// Check piles
		for (int i = 0; i < NUMBER_OF_PILES; ++i)
		{
			if (this.allPiles[i].isInPile(ix, iy))
			{

				// TODO
                // V this.allPiles[i] je pile z ktoreho beriem karty
                // Z this.allPiles[i].selectPile(ix, iy) vrati ListOfCards list kariet ktore sa vybrali z balicka


				this.actualPile = this.allPiles[i];
				this.actualList = this.allPiles[i].selectPile(ix, iy);

				if (this.actualList != null)
				{
					//this.actualList.printDebug();
					this.actualList.setIsDragged(true);
					this.actualList.setDifPosition(ix, iy);
				}
			}
		}
	}

	/**
	 * Vypustenie aktualnej karty a solitaire.pile-u
	 */
	public void mouseReleased(int ix, int iy)
	{
		if (this.actualList != null)
		{
			for (int i = 0; i < NUMBER_OF_PILES; ++i)
			{
				if (this.allPiles[i].isInPile(ix, iy))
				{

					// TODO
                    // Metoda this.allPiles[i].insertCard(ListOfCards) da karty do pilu a vrati True/False ci karty boli vlozene
                    // Volanie this.actualPile.actionEnded() obrati kartu na vrchu balicka
                    // Tu sa generuje UNDO este


					boolean wasInserted = this.allPiles[i].insertCard(this.actualList);
					if (!wasInserted)
					{
						// Vyskoci a urobit return karty
						break;
					}

					boolean actionEnded = this.actualPile.actionEnded();
					this.actualList.setIsDragged(false);

					// Generuje UNDO command
					if (this.allPiles[i] != this.actualPile)
					{
						this.undoList.add(new Command(
							this.actualPile, this.allPiles[i],
							this.actualList, actionEnded
							));
					}

					this.actualPile = null;
					this.actualList = null;
					return;
				}
			}

			// TODO
            // Vratenie kariet do povodneho balicka ak neboli insertnute

			this.actualPile.returnListOfCardsToPile(this.actualList, false);
			this.actualList.setIsDragged(false);
		}

		this.actualPile = null;
		this.actualList = null;
	}

	/**
	 * Pohyb karty
	 * Nastanovanie actualPosition pre kartu
	 */
	public void mouseDragged(int ix, int iy)
	{
		if (this.actualList != null)
		{
			this.actualList.setActualPosition(ix, iy);
		}
	}

	public int getXStartPosition() { return xStartPosition; }
	public int getYStartPosition() { return yStartPosition; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public int getCardWidth() { return CARD_WIDTH[this.gameMod]; }
	public int getCardHeight() { return CARD_HEIGHT[this.gameMod]; }
	public int getCardShift() { return Y_CARD_SHIFT[this.gameMod]; }

	public boolean getGameEnded() { return this.gameEnded; }
}
