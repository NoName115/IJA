package src;

import java.awt.Graphics;


public class Card
{
	private int xActualPosition;
	private int yActualPosition;
	private int xDefaultPosition;
	private int yDefaultPosition;

	private int width;
	private int height;

	private char number;
	private String type;
	private boolean isReaveled;

	//priavte Image

	public void update()
	{
		
	}

	public void render(Graphics g)
	{
		
	}

	public void setDefaultPosition(int x, int y)
	{
		this.xDefaultPosition = x;
		this.yDefaultPosition = y;
	}
}
