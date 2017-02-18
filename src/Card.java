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

	private boolean isDragged;

	//priavte Image

	public Card(String cardNumber, String cardType)
	{
		this.number = cardNumber;
		this.type = cardType;
		this.isReaveled = false;
		this.isDragged = false;
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
	}

	public void setActualPosition(int x, int y)
	{
		this.xActualPosition = x;
		this.yActualPosition = y;

		//this.xActualPosition = this.xDefaultPosition - x;
		//this.yActualPosition = this.yDefaultPosition - y;
	}

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
}
