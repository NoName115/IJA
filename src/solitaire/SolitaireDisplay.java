package solitaire;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class SolitaireDisplay extends JComponent implements MouseListener, ActionListener
{
	private static final String GAME_TITLE = "IJA - Solitaire";

	private static final int SPACING = 10;
	private static final int FACE_UP_OFFSET = 15;  //distance for cascading face-up add
	private static final int FACE_DOWN_OFFSET = 5;  //distance for cascading face-down add
	private static final int[] CARD_WIDTH = new int[] { 110, 73 };
	private static final int[] CARD_HEIGHT = new int[] { 146, 97 };
	//private static final int[] SPACING = new int[] { 30, 10 };  //distance between cards

	private int gameMod = 0;
	private boolean[] gameRunning = new boolean[] { true, false, false, false };

	private JFrame frame;
	private int[] selectedRow = new int[] { -1, -1, -1, -1 };
	private int[] selectedCol = new int[] { -1, -1, -1, -1 };
	private SolitaireClient game;

	// GUI
	MenuBar menuBar = new MenuBar();

	Menu game_1 = new Menu("Game - 1");
	MenuItem createPlayGround_1 = new MenuItem("Create game");
	MenuItem loadPlayGround_1 = new MenuItem("Load game");
	MenuItem savePlayGround_1 = new MenuItem("Save game");
	MenuItem undoPlayGround_1 = new MenuItem("Undo");
	MenuItem hintPlayGround_1 = new MenuItem("Show hint");

	Menu game_2 = new Menu("Game - 2");
	MenuItem createPlayGround_2 = new MenuItem("Create game");
	MenuItem loadPlayGround_2 = new MenuItem("Load game");
	MenuItem savePlayGround_2 = new MenuItem("Save game");
	MenuItem undoPlayGround_2 = new MenuItem("Undo");
	MenuItem hintPlayGround_2 = new MenuItem("Show hint");

	Menu game_3 = new Menu("Game - 3");
	MenuItem createPlayGround_3 = new MenuItem("Create game");
	MenuItem loadPlayGround_3 = new MenuItem("Load game");
	MenuItem savePlayGround_3 = new MenuItem("Save game");
	MenuItem undoPlayGround_3 = new MenuItem("Undo");
	MenuItem hintPlayGround_3 = new MenuItem("Show hint");

	Menu game_4 = new Menu("Game - 4");
	MenuItem createPlayGround_4 = new MenuItem("Create game");
	MenuItem loadPlayGround_4 = new MenuItem("Load game");
	MenuItem savePlayGround_4 = new MenuItem("Save game");
	MenuItem undoPlayGround_4 = new MenuItem("Undo");
	MenuItem hintPlayGround_4 = new MenuItem("Show hint");

	public SolitaireDisplay(SolitaireClient game)
	{
		this.game = game;

		frame = new JFrame("solitaire.SolitaireClient");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);

		this.setPreferredSize(new Dimension(
			(CARD_WIDTH[1] * 7 + SPACING * 8) * 2,
			(CARD_HEIGHT[1] * 2 + SPACING * 3 + FACE_DOWN_OFFSET * 7 + 13 * FACE_UP_OFFSET) * 2
			));
		this.addMouseListener(this);

		// Add GUI
		frame.setMenuBar(menuBar);

		menuBar.add(game_1);
		game_1.add(createPlayGround_1);
		game_1.add(loadPlayGround_1);
		game_1.add(savePlayGround_1);
		game_1.add(undoPlayGround_1);
		game_1.add(hintPlayGround_1);
		createPlayGround_1.addActionListener(this);
		loadPlayGround_1.addActionListener(this);
		savePlayGround_1.addActionListener(this);
		undoPlayGround_1.addActionListener(this);
		hintPlayGround_1.addActionListener(this);

		menuBar.add(game_2);
		game_2.add(createPlayGround_2);
		game_2.add(loadPlayGround_2);
		game_2.add(savePlayGround_2);
		game_2.add(undoPlayGround_2);
		game_2.add(hintPlayGround_2);
		createPlayGround_2.addActionListener(this);
		loadPlayGround_2.addActionListener(this);
		savePlayGround_2.addActionListener(this);
		undoPlayGround_2.addActionListener(this);
		hintPlayGround_2.addActionListener(this);

		menuBar.add(game_3);
		game_3.add(createPlayGround_3);
		game_3.add(loadPlayGround_3);
		game_3.add(savePlayGround_3);
		game_3.add(undoPlayGround_3);
		game_3.add(hintPlayGround_3);
		createPlayGround_3.addActionListener(this);
		loadPlayGround_3.addActionListener(this);
		savePlayGround_3.addActionListener(this);
		undoPlayGround_3.addActionListener(this);
		hintPlayGround_3.addActionListener(this);

		menuBar.add(game_4);
		game_4.add(createPlayGround_4);
		game_4.add(loadPlayGround_4);
		game_4.add(savePlayGround_4);
		game_4.add(undoPlayGround_4);
		game_4.add(hintPlayGround_4);
		createPlayGround_4.addActionListener(this);
		loadPlayGround_4.addActionListener(this);
		savePlayGround_4.addActionListener(this);
		undoPlayGround_4.addActionListener(this);
		hintPlayGround_4.addActionListener(this);

		frame.pack();
		frame.setTitle(GAME_TITLE);
		frame.setVisible(true);
	}

	public void setGameRunning(int gameIndex, boolean isRunning)
	{
		this.gameRunning[gameIndex] = isRunning;
		this.setGameMod();
	}

	private void setGameMod()
	{
		int counter = 0;
		for (int i = 0; i < 4; ++i)
		{
			if (this.gameRunning[i])
			{
				counter++;
			}
		}

		if (counter == 1)
		{
			this.gameMod = 0;
		}
		else if (counter > 1)
		{
			this.gameMod = 1;
		}
		else
		{
			System.out.println("Invalid number of games");
		}
	}

	private int getIndexOfRunningGame()
	{
		for (int i = 0; i < 4; ++i)
		{
			if (this.gameRunning[i])
			{
				return i;
			}
		}

		return -1;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == createPlayGround_1)
		{
			System.out.println("CREATE PLAYGOURND 1");
		}
		else if (e.getSource() == loadPlayGround_1)
		{
			System.out.println("LOADGAME 1");
		}
		else if (e.getSource() == savePlayGround_1)
		{
			System.out.println("SAVE GAME 1");
		}
		else if (e.getSource() == undoPlayGround_1)
		{
			System.out.println("UNDO GAME 1");
		}
		else if (e.getSource() == hintPlayGround_1)
		{
			System.out.println("HINT GAME 1");
		}
		else if (e.getSource() == createPlayGround_2)
		{
			System.out.println("CREATE PLAYGOURND 2");
		}
		else if (e.getSource() == loadPlayGround_2)
		{
			System.out.println("LOADGAME 2");
		}
		else if (e.getSource() == savePlayGround_2)
		{
			System.out.println("SAVE GAME 2");
		}
		else if (e.getSource() == undoPlayGround_2)
		{
			System.out.println("UNDO GAME 2");
		}
		else if (e.getSource() == hintPlayGround_2)
		{
			System.out.println("HINT GAME 2");
		}
		else if (e.getSource() == createPlayGround_3)
		{
			System.out.println("CREATE PLAYGOURND 3");
		}
		else if (e.getSource() == loadPlayGround_3)
		{
			System.out.println("LOADGAME 3");
		}
		else if (e.getSource() == savePlayGround_3)
		{
			System.out.println("SAVE GAME 3");
		}
		else if (e.getSource() == undoPlayGround_3)
		{
			System.out.println("UNDO GAME 3");
		}
		else if (e.getSource() == hintPlayGround_3)
		{
			System.out.println("HINT GAME 3");
		}
		else if (e.getSource() == createPlayGround_4)
		{
			System.out.println("CREATE PLAYGOURND 4");
		}
		else if (e.getSource() == loadPlayGround_4)
		{
			System.out.println("LOADGAME 4");
		}
		else if (e.getSource() == savePlayGround_4)
		{
			System.out.println("SAVE GAME 4");
		}
		else if (e.getSource() == undoPlayGround_4)
		{
			System.out.println("UNDO GAME 4");
		}
		else if (e.getSource() == hintPlayGround_4)
		{
			System.out.println("HINT GAME 4");
		}
	}

	public void paintComponent(Graphics g)
	{
		if (this.gameMod == 0)
		{
			int indexOfRunningGame = this.getIndexOfRunningGame();

			// Background
			g.setColor(new Color(200, 120, 50));
			g.fillRect(0, 0, getWidth(), getHeight());

			// Vykreslenie stockKarty
			drawCard(g, game.getStockCard(indexOfRunningGame), SPACING, SPACING);

			// Pozadie wastePile-u
			g.drawRect(SPACING * 2 + CARD_WIDTH[0], SPACING, CARD_WIDTH[0], CARD_HEIGHT[0]);
			drawCard(g, game.getWasteCard(indexOfRunningGame), SPACING * 2 + CARD_WIDTH[0], SPACING);
			// Vykreslenie ohranicenia, ked je karta selectnuta
			if (selectedRow[indexOfRunningGame] == 0 && selectedCol[indexOfRunningGame] == 1)
			{
				drawBorder(g, SPACING * 2 + CARD_WIDTH[0], SPACING);
			}

			// Aces
			for (int i = 0; i < 4; ++i)
			{
				drawCard(
					g,
					game.getFoundationCard(i, indexOfRunningGame),
					SPACING * (4 + i) + CARD_WIDTH[0] * (3 + i),
					SPACING
					);
			}

			// Linked piles
			for (int i = 0; i < 7; ++i)
			{
				// Vykreslenie pozadia
				g.setColor(Color.BLACK);
				g.drawRect(
					SPACING * (1 + i) + CARD_WIDTH[0] * i,
					SPACING * 2 + CARD_HEIGHT[0],
					CARD_WIDTH[0], CARD_HEIGHT[0]
					);

				Stack<Card> pile = game.getPile(i);
				int offset = 0;

				for (int j = 0; j < pile.size(); ++j)
				{
					drawCard(
						g,
						pile.get(j),
						SPACING + (CARD_WIDTH[0] + SPACING) * i,
						CARD_HEIGHT[0] + 2 * SPACING + offset
						);

					if (selectedRow[indexOfRunningGame] == 1 && selectedCol[indexOfRunningGame] == i && j == pile.size() - 1)
					{
						drawBorder(
							g,
							SPACING + (CARD_WIDTH[0] + SPACING) * i,
							CARD_HEIGHT[0] + 2 * SPACING + offset
							);
					}

					offset += pile.get(j).isFaceUp() == true ? FACE_UP_OFFSET : FACE_DOWN_OFFSET;
				}
			}
		}
		else
		{
			// TODO
		}

		/*
		//background
		g.setColor(new Color(225, 194, 0));
		g.fillRect(0, 0, getWidth(), getHeight());

		//face down
		drawCard(g, game.getStockCard(), SPACING, SPACING);
		*/

		/*
		//stock
		drawCard(g, game.getWasteCard(), SPACING * 2 + CARD_WIDTH, SPACING);
		if (selectedRow == 0 && selectedCol == 1)
		{
			drawBorder(g, SPACING * 2 + CARD_WIDTH, SPACING);
		}
		*/
		/*
		//aces
		for (int i = 0; i < 4; i++)
		{
			drawCard(g, game.getFoundationCard(i), SPACING * (4 + i) + CARD_WIDTH * (3 + i), SPACING);
		}
		*/

		/*
		//solitaire.piles
		for (int i = 0; i < 7; i++)
		{
			Stack<Card> pile = game.getPile(i);
			int offset = 0;
			for (int j = 0; j < pile.size(); j++)
			{
				drawCard(g, pile.get(j), SPACING + (CARD_WIDTH + SPACING) * i, CARD_HEIGHT + 2 * SPACING + offset);
				if (selectedRow == 1 && selectedCol == i && j == pile.size() - 1)
				{
					drawBorder(g, SPACING + (CARD_WIDTH + SPACING) * i, CARD_HEIGHT + 2 * SPACING + offset);
				}

				if (pile.get(j).isFaceUp())
				{
					offset += FACE_UP_OFFSET;
				}
				else
				{
					offset += FACE_DOWN_OFFSET;
				}
			}
		}
		*/
	}

	private void drawCard(Graphics g, Card card, int x, int y)
	{
		if (card == null)
		{
			g.setColor(Color.BLACK);
			g.drawRect(x, y, CARD_WIDTH[this.gameMod], CARD_HEIGHT[this.gameMod]);
		}
		else
		{
			String fileName = card.getFileName();
			File pathToFile = new File(fileName);
			Image image;

			try {
				image = ImageIO.read(pathToFile);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
//			Image image = new ImageIcon(fileName).getImage();
			g.drawImage(image, x, y, CARD_WIDTH[this.gameMod], CARD_HEIGHT[this.gameMod], null);
		}
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
		//none selected previously
		int col = e.getX() / (SPACING + CARD_WIDTH[0]);
		int row = e.getY() / (SPACING + CARD_HEIGHT[0]);
		if (row > 1)
		{
			row = 1;
		}
		if (col > 6)
		{
			col = 6;
		}

		if (row == 0 && col == 0)
		{
			game.stockClicked();
		}
		else if (row == 0 && col == 1)
		{
			game.wasteClicked();
		}
		else if (row == 0 && col >= 3)
		{
			game.tableauClicked(col - 3);
		}
		else if (row == 1)
		{
			game.foundationClicked(col);
		}

		repaint();
	}

	private void drawBorder(Graphics g, int x, int y)
	{
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, CARD_WIDTH[this.gameMod], CARD_HEIGHT[this.gameMod]);
		g.drawRect(x + 1, y + 1, CARD_WIDTH[this.gameMod] - 2, CARD_HEIGHT[this.gameMod] - 2);
		g.drawRect(x + 2, y + 2, CARD_WIDTH[this.gameMod] - 4, CARD_HEIGHT[this.gameMod] - 4);
	}

	// TODO
	// Index playground-u
	// NAMIESTO 0 dat gameIndex
	public void unselect(int gameIndex)
	{
		selectedRow[0] = -1;
		selectedCol[0] = -1;
	}

	public boolean isWasteSelected(int gameIndex)
	{
		return selectedRow[0] == 0 && selectedCol[0] == 1;
	}

	public void selectWaste(int gameIndex)
	{
		selectedRow[0] = 0;
		selectedCol[0] = 1;
	}

	public boolean isPileSelected(int gameIndex)
	{
		return selectedRow[0] == 1;
	}

	public int selectedPile(int gameIndex)
	{
		if (selectedRow[0] == 1)
		{
			return selectedCol[0];
		}

		return -1;
	}

	public void selectPile(int index, int gameIndex)
	{
		selectedRow[0] = 1;
		selectedCol[0] = index;
	}
}