package src;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.image.BufferStrategy;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;

import java.lang.InterruptedException;


public class GameController extends Canvas implements Runnable, MouseListener, MouseMotionListener
{
	private static final int WIDTH = 1220;
	private static final int HEIGHT = 900;
	private static final int NUMBER_OF_GAMES = 4;

	private boolean isRunning;
	private ArrayList<PlayGround> listOfGames;
	private int actualGameIndex;

	// Konstruktor
	public GameController()
	{
		super();	// Konstruktor pre Canvas
		this.isRunning = false;
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.actualGameIndex = -1;
		this.listOfGames = new ArrayList<PlayGround>();

		for (int i = 0; i < NUMBER_OF_GAMES; ++i)
		{
			this.listOfGames.add(null);
		}
	}

	public void addGame()
	{
		if (listOfGames.get(0) == null)
		{
			this.listOfGames.set(0, new PlayGround(
				0,
				0,
				WIDTH / 2,
				HEIGHT / 2
				)
			);
			return;
		}

		if (listOfGames.get(1) == null)
		{
			this.listOfGames.set(1, new PlayGround(
				WIDTH / 2,
				0,
				WIDTH / 2,
				HEIGHT / 2
				)
			);
			return;
		}

		if (listOfGames.get(2) == null)
		{
			this.listOfGames.set(2, new PlayGround(
				0,
				HEIGHT / 2,
				WIDTH / 2,
				HEIGHT / 2
				)
			);
			return;
		}

		if (listOfGames.get(3) == null)
		{
			this.listOfGames.set(3, new PlayGround(
				WIDTH / 2,
				HEIGHT / 2,
				WIDTH / 2,
				HEIGHT / 2
				)
			);
			return;
		}
	}

	public void closeGame(int index)
	{
		listOfGames.set(index, null);
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
		for (PlayGround pg : this.listOfGames)
		{
			if (pg != null)
			{
				pg.update();
			}
		}
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

		g.clearRect(0, 0, WIDTH, HEIGHT);

		for (PlayGround pg : this.listOfGames)
		{
			if (pg != null)
			{
				pg.render(g);
			}
		}

		g.dispose();
		buffer.show();
	}

	public void mousePressed(MouseEvent e)
	{
		for (int i = 0; i < listOfGames.size(); ++i)
		{
			PlayGround tempPg;
			if ((tempPg = listOfGames.get(i)) != null)
			{
				if (tempPg.checkSection(e.getX(), e.getY()))
				{
					this.actualGameIndex = i;
					tempPg.mousePressed(e.getX(), e.getY());
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		if (this.actualGameIndex != -1 && listOfGames.get(this.actualGameIndex) != null)
		{
			listOfGames.get(this.actualGameIndex).mouseReleased(e.getX(), e.getY());
			this.actualGameIndex = -1;
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		if (this.actualGameIndex != -1 && listOfGames.get(this.actualGameIndex) != null)
		{
			listOfGames.get(this.actualGameIndex).mouseDragged(e.getX(), e.getY());
		}
	}

	// Nepotrebane funkcie, musia byt definovane
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	

	public int getWidth()
	{
		return WIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}

}
