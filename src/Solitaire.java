package src;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


// Hlavna trieda a Main
public class Solitaire extends JFrame implements ActionListener
{
	private static final String GAME_TITLE = "Solitaire";

	GameController gameController = new GameController();

	// GUI
	MenuBar menuBar = new MenuBar();
	Menu mainMenu = new Menu("Game");

	MenuItem addGame = new MenuItem("Add Game", new MenuShortcut(KeyEvent.VK_A));
	MenuItem closeGame_1 = new MenuItem("Close Game_1");
	MenuItem closeGame_2 = new MenuItem("Close Game_2");
	MenuItem closeGame_3 = new MenuItem("Close Game_3");
	MenuItem closeGame_4 = new MenuItem("Close Game_4");

	public static void main(String[] args)
	{
		Solitaire game = new Solitaire();
		game.init();
	}

	private void init()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.add(gameController);
		this.setMenuBar(menuBar);
		menuBar.add(mainMenu);

		mainMenu.add(addGame);
		mainMenu.add(closeGame_1);
		mainMenu.add(closeGame_2);
		mainMenu.add(closeGame_3);
		mainMenu.add(closeGame_4);

		addGame.addActionListener(this);
		closeGame_1.addActionListener(this);
		closeGame_2.addActionListener(this);
		closeGame_3.addActionListener(this);
		closeGame_4.addActionListener(this);

		this.pack();
		this.setTitle(GAME_TITLE);
		this.setResizable(false);
		this.setVisible(true);

		gameController.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == addGame)
		{
			gameController.addGame();
		}

		if (e.getSource() == closeGame_1)
		{
			gameController.closeGame(0);
		}

		if (e.getSource() == closeGame_2)
		{
			gameController.closeGame(1);
		}

		if (e.getSource() == closeGame_3)
		{
			gameController.closeGame(2);
		}

		if (e.getSource() == closeGame_4)
		{
			gameController.closeGame(3);
		}
	}
}
