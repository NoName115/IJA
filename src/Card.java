package src;

import java.awt.Graphics;
import java.awt.Color;


public class Card
{
	private int xActualPosition;
	private int yActualPosition;
	private int xDefaultPosition;
	private int yDefaultPosition;

	private static final int width = 80;
	private static final int height = 120;

	private String number;
	private String type;
	private boolean isReaveled;

	//priavte Image

	public Card(String cardNumber, String cardType)
	{
		this.number = cardNumber;
		this.type = cardType;
		this.isReaveled = false;
	}

	public void update()
	{
		
	}

	public void render(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(this.xDefaultPosition, this.yDefaultPosition, this.width, this.height);

		g.setColor(Color.WHITE);
		g.fillRect(this.xDefaultPosition + 1, this.yDefaultPosition + 1, this.width - 2, this.height - 2);

		g.setColor(Color.BLACK);
		g.drawString(number + " " + type, this.xDefaultPosition + 10, this.yDefaultPosition + 20);
	}

	public void setDefaultPosition(int x, int y)
	{
		this.xDefaultPosition = x;
		this.yDefaultPosition = y;
	}
}
