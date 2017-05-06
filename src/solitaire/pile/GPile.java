package solitaire.pile;

import solitaire.card.Card;
import solitaire.card.GCard;
import solitaire.card.ListOfCards;

import java.awt.*;
import java.util.ArrayList;

public abstract class GPile {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;

    protected ArrayList<GCard> cardList;

    public GPile(int xPos, int yPos, int width, int height) {

        this.xPosition = xPos;
        this.yPosition = yPos;
        this.width = width;
        this.height = height;
    }

    /**
     * Prida kartu na koniec balicku
     * a vola setDefaultPosition pre danu kartu
     */
    public void addCard(GCard inputCard)
    {
        this.cardList.add(inputCard);
        inputCard.setDefaultPosition(this.xPosition, this.yPosition);
    }

    /**
     * Kontroluje ci sa myska nachadza v Pile-e
     * NEPREPISUJE SA
     */
    public boolean isInPile(int ix, int iy)
    {
        if (this.xPosition <= ix && this.xPosition + this.width >= ix)
        {
            if (this.yPosition <= iy && this.yPosition + this.height >= iy)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Funkcia pre nastavenie noveho rozlisenia okna hry pri zmene pocte hier
     */
    public void setNewResolution(int xPos, int yPos, int width, int height)
    {
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.width = width;
        this.height = height;

        this.setNewDefaultPosition();
    }

    /**
     * Nastavi nove defaultPosition pre zoznamy kariet v Pile-e
     */
    public void setNewDefaultPosition()
    {
        for (GCard c : this.cardList)
        {
            c.setDefaultPosition(this.xPosition, this.yPosition);
        }
    }

    /**
     * Urobit akciu pri kliknuti na Pile
     * Ak je potrebne vrati object ListOfCards
     */
    public ListOfCards selectPile(int ix, int iy) { return null; }


    /**
     * Vola sa pocas hry
     * Vlozi kartu do Pile-u
     * Vrati true ak karta bola vlozena, inac false
     */
    public boolean insertCard(ListOfCards inputList) { return false; }
    abstract public void update();
    abstract public void render(Graphics g);
}
