package src.pile;

import java.awt.Graphics;

import java.util.ArrayList;

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
	protected ArrayList<Card> cardList;

	public Pile(int xPos, int yPos, int width, int height, PlayGround pg)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;
		this.pg = pg;
		this.cardList = new ArrayList<Card>();
	}

	abstract public void update();
	abstract public void render(Graphics g);

	/**
	 * Prida kartu na koniec balicku
	 * a vola setDefaultPosition pre danu kartu
	 */
	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
		inputCard.setDefaultPosition(this.xPosition, this.yPosition);
	}

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
	 * Nastavi nove defaultPosition pre zoznamy kariet v Pile-e
	 */
	public void setNewDefaultPosition()
	{
		for (Card c : this.cardList)
		{
			c.setDefaultPosition(this.xPosition, this.yPosition);
		}
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
	 * Vola sa pri undo, maze karty z pile-u
	 * VELMI, VELMI NEBEZPECNA funkcia !!!!!
	 * Nekontroluje sa ci sa maze spravna karta !!!!!
	 * Maze podla poctu alebo len vrchnu kartu a pod..
	 */
	public void removeCard(ListOfCards inputList) {}

	/**
	 * Do balicku kariet v danej Pile-e ulozi vstupny zoznam kariet
	 */
	public void returnListOfCardsToPile(ListOfCards inputList, boolean action) {}

	/**
	 * Vola sa len pri linkedPile
	 */
	public boolean actionEnded() { return false; }
}
