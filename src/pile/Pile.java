package src.pile;

import java.awt.Graphics;

import src.card.*;
import src.PlayGround;


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

	// Pouziva sa pri pridani kariet na zaciatku hry
	abstract public void addCard(Card inputCard);
	// Nastavi nove defaultPosition pre zoznamy kariet v Pile-e
	abstract public void setNewDefaultPosition();
	abstract public void update();
	abstract public void render(Graphics g);

	// Skontroluje ci mouse je v Pile
	// NEPREPISUJE SA
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

	// Iba v LinkedPile sa prepisuje
	public void setNewResolution(int xPos, int yPos, int width, int height)
	{
		this.xPosition = xPos;
		this.yPosition = yPos;
		this.width = width;
		this.height = height;
	}

	// Urobi akciu pri kliknuti na Pile
	// ak je potrebne vrati Card
	public ListOfCards selectPile(int ix, int iy) { return null; }

	// Vlozi kartu do Pile-u
	// Vrati true ak sa karta vlozila, inac false
	// Pouziva sa pri pridani karty pocas hry
	public boolean insertCard(ListOfCards inputList) { return false; }

	public void returnCard(ListOfCards inputList) {}

	public void actionEnded() {}
}
