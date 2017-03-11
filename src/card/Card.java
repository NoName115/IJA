package src.card;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

import src.pile.DeckPile;
import src.GameController;
import src.PlayGround;


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

	private int value;		// 1 - A, 2 - 2 ..., 11 - J, 12 - Q, 13 - K
	private DeckPile.Type type;
	private boolean isReaveled;
	private boolean isDragged;

	private PlayGround pg;

	//private Image

	public Card(int cardValue, DeckPile.Type cardType, PlayGround pg)
	{
		this.value = cardValue;
		this.type = cardType;
		this.isReaveled = false;
		this.isDragged = false;

		this.width = pg.getCardWidth();
		this.height = pg.getCardHeight();

		this.pg = pg;
	}

	public void update()
	{
		// TODO
		// Animacia vratenia karty do povodnej pozicie
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

			g.setColor(this.type.toColor());
			g.drawString(this.toString(), this.xActualPosition + 8, this.yActualPosition + 15);
		}
		else
		{
			g.setColor(Color.GRAY);
			g.fillRect(this.xActualPosition + 1, this.yActualPosition + 1, this.width - 1, this.height - 1);
		}
	}

	@Override
	public String toString()
	{
		String cardInString;
		switch(this.value)
		{
			case 1:
				cardInString = "A";
				break;
			case 11:
				cardInString = "J";
				break;
			case 12:
				cardInString = "Q";
				break;
			case 13:
				cardInString = "K";
				break;
			default:
				cardInString = Integer.toString(this.value);
				break;
		}

		return cardInString + "  -  " + this.type.toString();
	}

	public DeckPile.Type getType()
	{
		return this.type;
	}

	public int getValue()
	{
		return this.value;
	}

	public boolean typeEqual(Card inputCard)
	{
		return this.type == inputCard.getType();
	}

	public boolean colorEqual(Card inputCard)
	{
		return this.type.equalColor(inputCard.getType());
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
	public Point handlePosition(int x, int y)
	{
		this.xActualPosition = x - this.xDifPos;
		this.yActualPosition = y - this.yDifPos;

		return new Point(this.xActualPosition, this.yActualPosition);
	}

	public void faceUp() { this.isReaveled = true; }
	public void faceDown() { this.isReaveled = false; }

	public void setIsDragged(boolean iBool)
	{
		this.isDragged = iBool;
	}

	// Pouzite v LinkedPile
	public int getXDefaultPostion() { return this.xDefaultPosition; }
	public int getYDefaultPosition() { return this.yDefaultPosition; }

	public int getXDifPos() { return this.xDifPos; }
	public int getYDifPos() { return this.yDifPos; }


	public void printDebug()
	{
		System.out.println(this.toString());
	}
}
