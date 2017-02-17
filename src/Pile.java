package src;

import java.awt.Graphics;


public abstract class Pile
{
	protected int xPosition;
	protected int yPosition;
	protected int width;
	protected int height;

	abstract public void update();
	abstract public void render(Graphics g);

	// Skontroluje ci mouse je v Pile
	// NEPREPISUJE SA
	public boolean isInPile(int ix, int iy)
	{
		if (xPosition <= ix && xPosition + width >= ix)
		{
			if (yPosition <= iy && yPosition + height >= iy)
			{
				return true;
			}
		}

		return false;
	}

	// Urobi akciu pri kliknuti na Pile
	// ak je potrebne vrati Card
	public Card selectPile(int ix, int iy)
	{
		return null;
	}

	// Vlozi kartu do Pile-u
	// Vrati true ak sa karta vlozila, inac false
	// Pouziva sa pri pridani karty pocas hry
	public boolean insertCard(Card inputCard)
	{
		return false;
	}

	// Pouziva sa pri pridani kariet na zaciatku hry
	abstract public void addCard(Card inputCard);
}
