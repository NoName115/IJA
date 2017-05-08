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
	private static final int[] FACE_UP_OFFSET = new int[] { 30 ,15 };  //distance for cascading face-up add
	private static final int[] FACE_DOWN_OFFSET = new int[] { 10, 5 };  //distance for cascading face-down add
	private static final int[] CARD_WIDTH = new int[] { 146, 73 };
	private static final int[] CARD_HEIGHT = new int[] { 194, 97 };

	private int gameMod = 0;
	private boolean[] gameRunning = new boolean[] { false, false, false, false };

	private JFrame frame;
	private int[] selectedRow = new int[] { -1, -1, -1, -1 };
	private int[] selectedCol = new int[] { -1, -1, -1, -1 };
	private SolitaireClient game;

	private int gameSizeX;
	private int gameSizeY;

	// GUI
	MenuBar menuBar = new MenuBar();

	Menu game_1 = new Menu("Game - 1");
	MenuItem createPlayGround_1 = new MenuItem("Create game");
	MenuItem closePlayGround_1 = new MenuItem("Close game");
	MenuItem loadPlayGround_1 = new MenuItem("Load game");
	MenuItem savePlayGround_1 = new MenuItem("Save game");
	MenuItem undoPlayGround_1 = new MenuItem("Undo");
	MenuItem hintPlayGround_1 = new MenuItem("Show hint");

	Menu game_2 = new Menu("Game - 2");
	MenuItem createPlayGround_2 = new MenuItem("Create game");
	MenuItem closePlayGround_2 = new MenuItem("Close game");
	MenuItem loadPlayGround_2 = new MenuItem("Load game");
	MenuItem savePlayGround_2 = new MenuItem("Save game");
	MenuItem undoPlayGround_2 = new MenuItem("Undo");
	MenuItem hintPlayGround_2 = new MenuItem("Show hint");

	Menu game_3 = new Menu("Game - 3");
	MenuItem createPlayGround_3 = new MenuItem("Create game");
	MenuItem closePlayGround_3 = new MenuItem("Close game");
	MenuItem loadPlayGround_3 = new MenuItem("Load game");
	MenuItem savePlayGround_3 = new MenuItem("Save game");
	MenuItem undoPlayGround_3 = new MenuItem("Undo");
	MenuItem hintPlayGround_3 = new MenuItem("Show hint");

	Menu game_4 = new Menu("Game - 4");
	MenuItem createPlayGround_4 = new MenuItem("Create game");
	MenuItem closePlayGround_4 = new MenuItem("Close game");
	MenuItem loadPlayGround_4 = new MenuItem("Load game");
	MenuItem savePlayGround_4 = new MenuItem("Save game");
	MenuItem undoPlayGround_4 = new MenuItem("Undo");
	MenuItem hintPlayGround_4 = new MenuItem("Show hint");

	String gameID;

	public SolitaireDisplay(SolitaireClient game)
	{
		this.game = game;

		frame = new JFrame("solitaire.SolitaireClient");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);

		this.gameSizeX = CARD_WIDTH[1] * 7 + SPACING * 8;
		this.gameSizeY = CARD_HEIGHT[1] * 2 + SPACING * 3 + FACE_DOWN_OFFSET[1] * 7 + 13 * FACE_UP_OFFSET[1];

		this.setPreferredSize(new Dimension(
			gameSizeX * 2,
			gameSizeY * 2
			));
		this.addMouseListener(this);

		// Add GUI
		frame.setMenuBar(menuBar);

		menuBar.add(game_1);
		game_1.add(createPlayGround_1);
		game_1.add(closePlayGround_1);
		game_1.add(loadPlayGround_1);
		game_1.add(savePlayGround_1);
		game_1.add(undoPlayGround_1);
		game_1.add(hintPlayGround_1);
		createPlayGround_1.addActionListener(this);
		closePlayGround_1.addActionListener(this);
		loadPlayGround_1.addActionListener(this);
		savePlayGround_1.addActionListener(this);
		undoPlayGround_1.addActionListener(this);
		hintPlayGround_1.addActionListener(this);

		menuBar.add(game_2);
		game_2.add(createPlayGround_2);
		game_2.add(closePlayGround_2);
		game_2.add(loadPlayGround_2);
		game_2.add(savePlayGround_2);
		game_2.add(undoPlayGround_2);
		game_2.add(hintPlayGround_2);
		createPlayGround_2.addActionListener(this);
		closePlayGround_2.addActionListener(this);
		loadPlayGround_2.addActionListener(this);
		savePlayGround_2.addActionListener(this);
		undoPlayGround_2.addActionListener(this);
		hintPlayGround_2.addActionListener(this);

		menuBar.add(game_3);
		game_3.add(createPlayGround_3);
		game_3.add(closePlayGround_3);
		game_3.add(loadPlayGround_3);
		game_3.add(savePlayGround_3);
		game_3.add(undoPlayGround_3);
		game_3.add(hintPlayGround_3);
		createPlayGround_3.addActionListener(this);
		closePlayGround_3.addActionListener(this);
		loadPlayGround_3.addActionListener(this);
		savePlayGround_3.addActionListener(this);
		undoPlayGround_3.addActionListener(this);
		hintPlayGround_3.addActionListener(this);

		menuBar.add(game_4);
		game_4.add(createPlayGround_4);
		game_4.add(closePlayGround_4);
		game_4.add(loadPlayGround_4);
		game_4.add(savePlayGround_4);
		game_4.add(undoPlayGround_4);
		game_4.add(hintPlayGround_4);
		createPlayGround_4.addActionListener(this);
		closePlayGround_4.addActionListener(this);
		loadPlayGround_4.addActionListener(this);
		savePlayGround_4.addActionListener(this);
		undoPlayGround_4.addActionListener(this);
		hintPlayGround_4.addActionListener(this);

		frame.pack();
		frame.setTitle(GAME_TITLE);
		frame.setVisible(true);

		String input = (String) JOptionPane.showInputDialog(
			null,
			"Name:", "Connect to Solitaire server",
			JOptionPane.QUESTION_MESSAGE,
			null,
			null,
			"Game_ID"
			);

		if (input == null || input.trim().length() == 0)
		{
			this.game.startGame(null);
		}
		else
		{
			this.game.startGame(input.trim());
		}
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
			this.game.activateGame(0);
		}
		else if (e.getSource() == closePlayGround_1)
		{
			this.game.disableGame(0);
		}
		else if (e.getSource() == loadPlayGround_1)
		{
			this.game.loadGame(0);
		}
		else if (e.getSource() == savePlayGround_1)
		{
			this.game.saveGame(0);
		}
		else if (e.getSource() == undoPlayGround_1)
		{
			this.game.undo(0);
		}
		else if (e.getSource() == hintPlayGround_1)
		{
			this.game.hint(0);
		}
		else if (e.getSource() == createPlayGround_2)
		{
			this.game.activateGame(1);
		}
		else if (e.getSource() == closePlayGround_2)
		{
			this.game.disableGame(1);
		}
		else if (e.getSource() == loadPlayGround_2)
		{
			this.game.loadGame(1);
		}
		else if (e.getSource() == savePlayGround_2)
		{
			this.game.saveGame(1);
		}
		else if (e.getSource() == undoPlayGround_2)
		{
			this.game.undo(1);
		}
		else if (e.getSource() == hintPlayGround_2)
		{
			this.game.hint(2);
		}
		else if (e.getSource() == createPlayGround_3)
		{
			this.game.activateGame(2);
		}
		else if (e.getSource() == closePlayGround_3)
		{
			this.game.disableGame(2);
		}
		else if (e.getSource() == loadPlayGround_3)
		{
			this.game.loadGame(2);
		}
		else if (e.getSource() == savePlayGround_3)
		{
			this.game.saveGame(2);
		}
		else if (e.getSource() == undoPlayGround_3)
		{
			this.game.undo(2);
		}
		else if (e.getSource() == hintPlayGround_3)
		{
			this.game.hint(2);
		}
		else if (e.getSource() == createPlayGround_4)
		{
			this.game.activateGame(3);
		}
		else if (e.getSource() == closePlayGround_4)
		{
			this.game.disableGame(3);
		}
		else if (e.getSource() == loadPlayGround_4)
		{
			this.game.loadGame(3);
		}
		else if (e.getSource() == savePlayGround_4)
		{
			this.game.saveGame(3);
		}
		else if (e.getSource() == undoPlayGround_4)
		{
			this.game.undo(3);
		}
		else if (e.getSource() == hintPlayGround_4)
		{
			this.game.hint(3);
		}

		repaint();
	}

	public void paintComponent(Graphics g)
	{
		// Background
		g.setColor(new Color(200, 120, 50));
		g.fillRect(0, 0, getWidth(), getHeight());

		if (this.gameMod == 0)
		{
			int indexOfRunningGame = this.getIndexOfRunningGame();
			if (indexOfRunningGame != -1)
			{
				this.printGame(g, 0, 0, indexOfRunningGame);
			}
			else
			{
				return;
			}
		}
		else
		{
			for (int i = 0; i < 4; ++i)
			{
				// Ak bezi hra, vykresli sa obrazovka
				if (this.gameRunning[i])
				{
					// Nastavenie startovnych pozicii pre hru
					int xStartPos = 0;
					int yStartPos = 0;
					switch (i)
					{
						case 1:
							xStartPos = this.gameSizeX;
							yStartPos = 0;
							break;

						case 2:
							xStartPos = 0;
							yStartPos = this.gameSizeY;
							break;

						case 3:
							xStartPos = this.gameSizeX;
							yStartPos = this.gameSizeY;
							break;

						default:
							xStartPos = 0;
							yStartPos = 0;
					}

					this.printGame(g, xStartPos, yStartPos, i);
				}
			}
		}
	}

	private void printGame(Graphics g, int xStartPos, int yStartPos, int gameIndex)
	{
		// Vykreslenie stockKarty
		drawCard(g, game.getStockCard(gameIndex), SPACING + xStartPos, SPACING + yStartPos);

		// Pozadie wastePile-u
		g.setColor(Color.BLACK);
		g.drawRect(
			SPACING * 2 + CARD_WIDTH[this.gameMod] + xStartPos,
			SPACING  + yStartPos,
			CARD_WIDTH[this.gameMod],
			CARD_HEIGHT[this.gameMod]
			);
		drawCard(
			g,
			game.getWasteCard(gameIndex),
			SPACING * 2 + CARD_WIDTH[this.gameMod] + xStartPos,
			SPACING + yStartPos
			);
		// Vykreslenie ohranicenia, ked je karta selectnuta
		if (selectedRow[gameIndex] == 0 && selectedCol[gameIndex] == 1)
		{
			drawBorder(g, SPACING * 2 + CARD_WIDTH[this.gameMod] + xStartPos, SPACING + yStartPos);
		}

		// Aces
		for (int i = 0; i < 4; ++i)
		{
			drawCard(
				g,
				game.getFoundationCard(i, gameIndex),
				SPACING * (4 + i) + CARD_WIDTH[this.gameMod] * (3 + i) + xStartPos,
				SPACING + yStartPos
				);
		}

		// Linked piles
		for (int i = 0; i < 7; ++i)
		{
			// Vykreslenie pozadia
			g.setColor(Color.BLACK);
			g.drawRect(
				SPACING * (1 + i) + CARD_WIDTH[this.gameMod] * i + xStartPos,
				SPACING * 2 + CARD_HEIGHT[this.gameMod] + yStartPos,
				CARD_WIDTH[this.gameMod], CARD_HEIGHT[this.gameMod]
				);

			Stack<Card> pile = game.getPile(i, gameIndex);
			int offset = 0;

			for (int j = 0; j < pile.size(); ++j)
			{
				drawCard(
					g,
					pile.get(j),
					SPACING + (CARD_WIDTH[this.gameMod] + SPACING) * i + xStartPos,
					CARD_HEIGHT[this.gameMod] + 2 * SPACING + offset + yStartPos
					);

				if (selectedRow[gameIndex] == 1 && selectedCol[gameIndex] == i && j == pile.size() - 1)
				{
					drawBorder(
						g,
						SPACING + (CARD_WIDTH[this.gameMod] + SPACING) * i + xStartPos,
						CARD_HEIGHT[this.gameMod] + 2 * SPACING + offset + yStartPos
						);
				}

				offset += pile.get(j).isFaceUp() == true ? FACE_UP_OFFSET[this.gameMod] : FACE_DOWN_OFFSET[this.gameMod];
			}
		}
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
		if (this.gameMod == 0)
		{
			this.pileSelection(e.getX(), e.getY(), this.getIndexOfRunningGame());
		}
		else
		{
			for (int i = 0; i < 4; ++i)
			{
				// Ak bezi hra, zisti sa ktory pile bol oznaceny
				if (this.gameRunning[i])
				{
					int xStartPos = 0;
					int yStartPos = 0;
					int xMousePos = e.getX();
					int yMousePos = e.getY();

					if (i == 0)
					{
						if (xMousePos > this.gameSizeX || yMousePos > this.gameSizeY)
						{
							continue;
						}

						xStartPos = 0;
						yStartPos = 0;
					}
					else if (i == 1)
					{
						if (xMousePos < this.gameSizeX || yMousePos > this.gameSizeY)
						{
							continue;
						}

						xStartPos = this.gameSizeX;
						yStartPos = 0;
					}
					else if (i == 2)
					{
						if (xMousePos > this.gameSizeX || yMousePos < this.gameSizeY)
						{
							continue;
						}

						xStartPos = 0;
						yStartPos = this.gameSizeY;
					}
					else if (i == 3)
					{
						if (xMousePos < this.gameSizeX || yMousePos < this.gameSizeY)
						{
							continue;
						}

						xStartPos = this.gameSizeX;
						yStartPos = this.gameSizeY;
					}

					this.pileSelection(xMousePos - xStartPos, yMousePos - yStartPos, i);
				}
			}
		}

		repaint();
	}

	private void pileSelection(int xStartPos, int yStartPos, int gameIndex)
	{
		//none selected previously
		int col = xStartPos / (SPACING + CARD_WIDTH[this.gameMod]);
		int row = yStartPos / (SPACING + CARD_HEIGHT[this.gameMod]);
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
			game.stockClicked(gameIndex);
		}
		else if (row == 0 && col == 1)
		{
			game.wasteClicked(gameIndex);
		}
		else if (row == 0 && col >= 3)
		{
			game.tableauClicked(col - 3, gameIndex);
		}
		else if (row == 1)
		{
			game.foundationClicked(col, gameIndex);
		}
	}

	private void drawBorder(Graphics g, int x, int y)
	{
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, CARD_WIDTH[this.gameMod], CARD_HEIGHT[this.gameMod]);
		g.drawRect(x + 1, y + 1, CARD_WIDTH[this.gameMod] - 2, CARD_HEIGHT[this.gameMod] - 2);
		g.drawRect(x + 2, y + 2, CARD_WIDTH[this.gameMod] - 4, CARD_HEIGHT[this.gameMod] - 4);
	}

	public void showHint(String hint)
	{
		JOptionPane.showMessageDialog(this.frame, hint);
	}

	public void unselect(int gameIndex)
	{
		selectedRow[gameIndex] = -1;
		selectedCol[gameIndex] = -1;
	}

	public boolean isWasteSelected(int gameIndex)
	{
		return selectedRow[gameIndex] == 0 && selectedCol[gameIndex] == 1;
	}

	public void selectWaste(int gameIndex)
	{
		selectedRow[gameIndex] = 0;
		selectedCol[gameIndex] = 1;
	}

	public boolean isPileSelected(int gameIndex)
	{
		return selectedRow[gameIndex] == 1;
	}

	public int selectedPile(int gameIndex)
	{
		if (selectedRow[gameIndex] == 1)
		{
			return selectedCol[gameIndex];
		}

		return -1;
	}

	public void selectPile(int index, int gameIndex)
	{
		selectedRow[gameIndex] = 1;
		selectedCol[gameIndex] = index;
	}
}