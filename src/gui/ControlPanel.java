package src.gui;

import java.awt.Graphics;
import java.awt.Color;

import src.PlayGround;


public class ControlPanel
{
    private int xPosition;
    private int yPosition;
    private int height;
    private int width;

    public PlayGround game;

    // Buttons
    private ButtonHint buttonHint;
    private ButtonUndo buttonUndo;

    public ControlPanel(PlayGround mainGame, int xPosition, int yPosition, int width, int height)
    {
        this.game = mainGame;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;

        this.buttonHint = new ButtonHint(this, "Show Hint", 0.15, 0.8, 0.01);
        this.buttonUndo = new ButtonUndo(this, "Undo", 0.15, 0.56, 0.25);
    }

    public void render(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(xPosition, yPosition, width, height);

        buttonHint.render(g);
        buttonUndo.render(g);
    }

    public void setNewResolution(int xPosition, int yPosition, int width, int height)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;

        buttonHint.setNewResolution();
        buttonUndo.setNewResolution();
    }

    public boolean isIn(int x, int y)
    {
        if (buttonHint.isIn(x, y))
        {
            buttonHint.clicked();
            return true;
        }

        if (buttonUndo.isIn(x, y))
        {
            buttonUndo.clicked();
            return true;
        }

        return false;
    }

    public int getXPosition() { return this.xPosition; }
    public int getYPosition() { return this.yPosition; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
}