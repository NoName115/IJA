package src.pile;

import java.awt.Graphics;

import src.card.*;
import src.PlayGround;


/**
 * Trieda z ktorej dedia vsetky ostatne xxxPile
 */
public abstract class Pile
{
	protected int xPosition;
	protected int yPosition;
	protected int width;
	protected int height;
	protected PlayGround pg;

	public Pile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;
		this.pg = pg;
	}

	abstract public void update();
	abstract public void render(Graphics g);
	// Pouziva sa pri pridani kariet na zaciatku hry
	abstract public void addCard(Card inputCard);
	// Nastavi nove defaultPosition pre zoznamy kariet v Pile-e
	abstract public void setNewDefaultPosition();

	/**
	 * Kontroluje ci sa myska nachadza v Pile-e
	 * NEPREPISUJE SA
	 */
	public boolean isInPile(int ix, int iy)
	{
		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			if (this.yPosition <= iy && this.yPosition + this.height >= iy)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Funkcia pre nastavenie noveho rozlisenia okna hry pri zmene pocte hier
	 * 
	 */
	public void setNewResolution(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;

		this.setNewDefaultPosition();
	}

	/**
	 * Urobit akciu pri kliknuti na Pile
	 * Ak je potrebne vrati object ListOfCards
	 */
	public ListOfCards selectPile(int ix, int iy) { return null; }

	/**
	 * Vola sa pocas hry
	 * Vlozi kartu do Pile-u
	 * Vrati true ak karta bola vlozena, inac false
	 */
	public boolean insertCard(ListOfCards inputList) { return false; }

	/**
	 * Do balicku kariet v danej Pile-e ulozi vstupny zoznam kariet
	 */
	public void returnListOfCardsToPile(ListOfCards inputList) {}

	/**
	 * Vola sa len pri linkedPile
	 */
	public void actionEnded() {}
}
