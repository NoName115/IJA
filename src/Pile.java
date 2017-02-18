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
		if (this.xPosition <= ix && this.xPosition + this.width >= ix)
		{
			if (this.yPosition <= iy && this.yPosition + this.height >= iy)
			{
				return true;
			}
		}

		return false;
	}

	// Urobi akciu pri kliknuti na Pile
	// ak je potrebne vrati Card
	public CardOrList selectPile(int ix, int iy) { return null; }

	// Vlozi kartu do Pile-u
	// Vrati true ak sa karta vlozila, inac false
	// Pouziva sa pri pridani karty pocas hry
	public boolean insertCard(CardOrList inputCardOrList) { return false; }

	public void returnCard(CardOrList inputCardOrList) {}

	public void actionEnded() {}

	// Pouziva sa pri pridani kariet na zaciatku hry
	abstract public void addCard(Card inputCard);
}
