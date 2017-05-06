package solitaire.pile;

import java.util.ArrayList;
import solitaire.card.*;


public class DrawHelpPile_Server extends Pile_Server
{
    public DrawHelpPile_Server()
    {
        super();
    }

    @Override
    public void returnListOfCardsToPile(ListOfCards inputList, boolean action)
    {
        if (inputList == null || inputList.isEmpty() || inputList.size() > 1)
        {
            return;
        }

        for (Card c : inputList.getList())
        {
            this.cardList.add(c);
        }
    }
}