package src;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.image.BufferStrategy;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.lang.InterruptedException;


public class GameController extends Canvas implements Runnable, MouseListener, MouseMotionListener
{
	private static final int WIDTH = 1000;	//1500
	private static final int HEIGHT = 540;	//800
	private boolean isRunning;

	private static final int CARD_WIDTH = 20;
	private static final int CARD_HEIGHT = 20;

	private PlayGround firstPlayGround;

	// Konstruktor
	public GameController()
	{
		//super();	// Konstruktor pre Canvas
		this.isRunning = false;
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		firstPlayGround = new PlayGround(10, 10, WIDTH - 10, HEIGHT - 10);
	}

	// Loop pre hru
	// Spusti sa pri vytvoreni vlakna
	public void run()
	{
		long lastTimeCycle = System.nanoTime();
		long lastTimeOutput = System.currentTimeMillis();

		double nsPerTick = Math.pow(10, 9) / 60D;
		double unproccesedTicks = 0;

		int frames = 0;
		int ticks = 0;

		while (this.isRunning)
		{
			long nowTimeCycle = System.nanoTime();
			unproccesedTicks += (nowTimeCycle - lastTimeCycle) / nsPerTick;
			lastTimeCycle = nowTimeCycle;

			while (unproccesedTicks >= 1)
			{
				ticks++;
				unproccesedTicks -= 1;
				this.update();
			}

			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				System.out.println("Error:" + e);
			}

			frames++;
			this.render();

			if (System.currentTimeMillis() - lastTimeOutput >= 1000)
			{
				lastTimeOutput += 1000;
				//System.out.println("Ticks: " + ticks + " FPS: " + frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void start()
	{
		if (!this.isRunning)
		{
			this.isRunning = true;
			// Spusti sa run()
			new Thread(this).start();
		}
	}

	// Update pre logiku hry
	private void update()
	{
		firstPlayGround.update();
	}

	// Render hry
	private void render()
	{
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null)
		{
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();

		
		firstPlayGround.render(g);


		g.dispose();
		buffer.show();
	}

	public void mousePressed(MouseEvent e)
	{
		// Check sector
		if (firstPlayGround.getXStartPosition() <= e.getX() &&
			firstPlayGround.getXStartPosition() + firstPlayGround.getWidth() >= e.getX())
		{
			if (firstPlayGround.getYStartPosition() <= e.getY() &&
				firstPlayGround.getYStartPosition() + firstPlayGround.getHeight() >= e.getY())
			{
				firstPlayGround.mousePressed(e.getX(), e.getY());
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		//System.out.println("R: " + e.getX() + " : " + e.getY());
	}

	public void mouseMoved(MouseEvent e)
	{
		// TODO
	}

	// Nepotrebane funkcie, musia byt definovane
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

	public int getWidth()
	{
		return WIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}

}
