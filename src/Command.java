package src;

import java.util.ArrayList;

import src.card.*;
import src.pile.Pile;


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

        System.out.println(
            this.source.getClass().getSimpleName() + " - " +
            this.destination.getClass().getSimpleName()
            );
        this.cardList.printDebug();
    }

    public void run()
    {
        // TODO
        // Pre server-client
    }

    public void undo()
    {
        System.out.println(
            "\nUndo_run: \n" +
            this.source.getClass().getSimpleName() + " - " +
            this.destination.getClass().getSimpleName()
            );

        this.destination.removeCard(this.cardList);
        this.source.returnListOfCardsToPile(this.cardList, action);
    }
}
