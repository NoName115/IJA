package src;

import java.awt.Graphics;
import java.awt.Color;


public class Card
{
	private int xActualPosition;
	private int yActualPosition;
	private int xDefaultPosition;
	private int yDefaultPosition;

	private int xDifPos;
	private int yDifPos;

	private int width;
	private int height;

	private String number;
	private String type;
	private boolean isReaveled;
	private boolean isDragged;

	private PlayGround pg;

	//private Image

	public Card(String cardNumber, String cardType, PlayGround pg)
	{
		this.number = cardNumber;
		this.type = cardType;
		this.isReaveled = false;
		this.isDragged = false;

		this.width = pg.getCardWidth();
		this.height = pg.getCardHeight();

		this.pg = pg;
	}

	public void update()
	{
		// NOTHING
	}

	public void render(Graphics g)
	{
		if (!this.isDragged)
		{
			this.xActualPosition = this.xDefaultPosition;
			this.yActualPosition = this.yDefaultPosition;
		}

		g.setColor(Color.BLACK);
		g.drawRect(this.xActualPosition, this.yActualPosition, this.width, this.height);

		if (isReaveled)
		{
			g.setColor(Color.WHITE);
			g.fillRect(this.xActualPosition + 1, this.yActualPosition + 1, this.width - 1, this.height - 1);

			g.setColor(Color.BLACK);
			g.drawString(number + " " + type, this.xActualPosition + 10, this.yActualPosition + 15);
		}
		else
		{
			g.setColor(Color.GRAY);
			g.fillRect(this.xActualPosition + 1, this.yActualPosition + 1, this.width - 1, this.height - 1);
		}
	}

	public void setDefaultPosition(int x, int y)
	{
		this.xDefaultPosition = x;
		this.yDefaultPosition = y;

		this.width = this.pg.getCardWidth();
		this.height =  this.pg.getCardHeight();
	}

	// Volane pri mouseDragged
	public void setActualPosition(int x, int y)
	{
		this.xActualPosition = x;
		this.yActualPosition = y;
	}

	// Volane pri mousePressed
	public void setDifPosition(int x, int y)
	{
		this.xDifPos = x - this.xDefaultPosition;
		this.yDifPos = y - this.yDefaultPosition;
	}

	// Vola sa len pre 0-tu kartu v liste
	public void setHandlePosition(int x, int y)
	{
		this.xActualPosition = x - this.xDifPos;
		this.yActualPosition = y - this.yDifPos;
	}
	public int getHandleXPos(int x) { return x - this.xDifPos; }
	public int getHandleYPos(int y) { return y - this.yDifPos; }

	public void faceUp()
	{
		this.isReaveled = true;
	}

	public void faceDown()
	{
		this.isReaveled = false;
	}

	public void setIsDragged(boolean iBool)
	{
		this.isDragged = iBool;
	}

	public void printDebug()
	{
		System.out.println(number + " : " + type);
	}

	public int getXDefaultPostion()
	{
		return this.xDefaultPosition;
	}

	public int getYDefaultPosition()
	{
		return this.yDefaultPosition;
	}

	public int getYDifPos()
	{
		return this.yDifPos;
	}
}
