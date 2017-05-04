package solitaire.gui;

import java.awt.Graphics;
import java.awt.Color;


public abstract class Button
{
    protected int xPosition = 0;
    protected int yPosition = 0;
    protected int width = 0;
    protected int height = 0;

    protected double topSpacing;
    protected double leftSpacing;
    protected double rightSpacing;

    protected String buttonName;
    protected ControlPanel panel;

    public Button(ControlPanel panel, String buttonName, double topSpacing, double leftSpacing, double rightSpacing)
    {
        this.panel = panel;
        this.buttonName = buttonName;
        this.topSpacing = topSpacing;
        this.leftSpacing = leftSpacing;
        this.rightSpacing = rightSpacing;

        setNewResolution();
    }

    abstract public void clicked();

    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);

        g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
        g.drawString(this.buttonName, this.xPosition + this.width / 2 - 28, this.yPosition + this.height / 2 + 5);
    }

    public void setNewResolution()
    {
        this.xPosition = panel.getXPosition() + (int) (panel.getWidth() * leftSpacing);
        this.yPosition = panel.getYPosition() + (int) (panel.getHeight() * topSpacing);
        this.width = (int) ((1 - (leftSpacing + rightSpacing)) * panel.getWidth());
        this.height = (int) ((1 - topSpacing * 2) * panel.getHeight());
    }

    public boolean isIn(int x, int y)
    {
        if (this.xPosition <= x && this.xPosition + this.width >= x)
        {
            if (this.yPosition <= y && this.yPosition + this.height >= y)
            {
                return true;
            }
        }

        return false;
    }
}