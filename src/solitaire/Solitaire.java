package solitaire;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;

import java.awt.event.*;


public class Solitaire extends JFrame implements ActionListener
{
	private static final String GAME_TITLE = "Solitaire";

	GameController gameController = new GameController();

	// Main GUI
	MenuBar menuBar = new MenuBar();

	Menu mainMenu = new Menu("Game Menu");
	MenuItem createGame = new MenuItem("Create new game");
	MenuItem connectGame = new MenuItem("Connect to game");

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

	/**
	 * Hlavna funkcia main, vytvara object hry Solitaire
	 */
	public static void main(String[] args)
	{
		Solitaire game = new Solitaire();
		game.init();
	}

	/**
	 * Inicializacia zakladneho grafickeho rozhranie
	 * pre pridanie/odobranie hry
	 * A spustenie hry
	 */
	private void init()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent windowEvent) {
				super.windowClosed(windowEvent);
				gameController.getConnection().disconnect();
			}
		});

		this.add(gameController);
		this.setMenuBar(menuBar);

		menuBar.add(mainMenu);
		mainMenu.add(createGame);
		mainMenu.add(connectGame);
		createGame.addActionListener(this);
		connectGame.addActionListener(this);

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

		this.pack();
		this.setTitle(GAME_TITLE);
		this.setResizable(false);
		this.setVisible(true);

		// Spustenie hry
		gameController.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == createGame)
		{
			System.out.println("CREATE GAME");
		}
		else if (e.getSource() == connectGame)
		{
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
				System.exit(1);
			}

			String gameID = input.trim();

			System.out.println("CONNECT TO GAME _: " + gameID);
		}
		else if (e.getSource() == createPlayGround_1)
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
}
