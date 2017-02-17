package src;

import java.awt.Graphics;


public class PlayGround
{
	private int width;
	private int height;
	private int xStartPosition;
	private int yStartPosition;

	private Card actualCard;

	public PlayGround(int xPos, int yPos, int width, int height)
	{
		this.xStartPosition = xPos;
		this.yStartPosition = yPos;
		this.width = width;
		this.height = height;

		this.actualCard = null;
	}

	// Update pre logiku hry
	private void update()
	{
		return;
	}

	// Render hry
	private void render(Graphics g)
	{
		return;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

}
