package solitaire.card;

import solitaire.PlayGround;

import java.awt.*;

public class GCard {

    private PlayGround pg;

    private int xActualPosition;
    private int yActualPosition;
    private int xDefaultPosition;
    private int yDefaultPosition;

    private int xDifPos;
    private int yDifPos;

    private int width;
    private int height;

    private boolean isDragged;

    private Card card;

    public GCard(Card card, int width, int height) {
        this.card = card;
        this.width = width;
        this.height = height;

        this.isDragged = false;
    }

    public void update()
    {
        // TODO
        // Animacia vratenia karty do povodnej pozicie
    }

    public void render(Graphics g)
    {
        if (!this.isDragged)
        {
            this.xActualPosition = this.xDefaultPosition;
            this.yActualPosition = this.yDefaultPosition;
        }

        g.setColor(Color.BLACK);
        g.drawRect(this.xActualPosition, this.yActualPosition, this.width, this.height);

        if (card.isReaveled())
        {
            g.setColor(Color.WHITE);
            g.fillRect(this.xActualPosition + 1, this.yActualPosition + 1, this.width - 1, this.height - 1);

            g.setColor(this.card.getType().toColor());
            g.drawString(this.toString(), this.xActualPosition + 8, this.yActualPosition + 15);
        }
        else
        {
            g.setColor(Color.GRAY);
            g.fillRect(this.xActualPosition + 1, this.yActualPosition + 1, this.width - 1, this.height - 1);
        }
    }

    public void setDefaultPosition(int x, int y)
    {
        this.xDefaultPosition = x;
        this.yDefaultPosition = y;

        this.width = this.pg.getCardWidth();
        this.height =  this.pg.getCardHeight();
    }

    // Volane pri mouseDragged
    public void setActualPosition(int x, int y)
    {
        this.xActualPosition = x;
        this.yActualPosition = y;
    }

    // Volane pri mousePressed
    public void setDifPosition(int x, int y)
    {
        this.xDifPos = x - this.xDefaultPosition;
        this.yDifPos = y - this.yDefaultPosition;
    }

    // Vola sa len pre 0-tu kartu v liste
    public Point handlePosition(int x, int y)
    {
        this.xActualPosition = x - this.xDifPos;
        this.yActualPosition = y - this.yDifPos;

        return new Point(this.xActualPosition, this.yActualPosition);
    }

    public void setIsDragged(boolean iBool)
    {
        this.isDragged = iBool;
    }

    // Pouzite v LinkedPile
    public int getXDefaultPostion() { return this.xDefaultPosition; }
    public int getYDefaultPosition() { return this.yDefaultPosition; }

    public int getXDifPos() { return this.xDifPos; }
    public int getYDifPos() { return this.yDifPos; }


    public void printDebug()
    {
        System.out.println(this.toString());
    }
}
