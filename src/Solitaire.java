package src;

import javax.swing.JFrame;
import java.awt.BorderLayout;

// Hlavny trieda a Main
public class Solitaire extends JFrame
{
	private static final String GAME_TITLE = "Solitaire";

	GameController controllerCanvas = new GameController();

	public static void main(String[] args)
	{
		Solitaire game = new Solitaire();
		game.init();
	}

	private void init()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.add(controllerCanvas);

		this.pack();
		this.setTitle(GAME_TITLE);
		this.setResizable(false);
		this.setVisible(true);

		controllerCanvas.start();
	}
}
