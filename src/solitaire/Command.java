package solitaire;

import java.util.ArrayList;

import solitaire.card.*;
import solitaire.pile.Pile;


public class Command
{
    protected Pile source;
    protected Pile destination;
    protected ListOfCards cardList;
    protected boolean action;

    public Command(Pile source, Pile destination, ListOfCards cardList, boolean action)
    {
        this.source = source;
        this.destination = destination;
        this.cardList = cardList;
        this.action = action;
    }

    public void run()
    {
        // TODO
        // Pre server-client
    }

    public void undo()
    {
        this.destination.removeCard(this.cardList);
        this.source.returnListOfCardsToPile(this.cardList, action);
    }
}
