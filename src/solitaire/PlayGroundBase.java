package solitaire;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

import solitaire.pile.*;
import solitaire.card.*;

/**
 * Object reprezentujuci jednu hraciu plochu, bez renderu
*/
public class PlayGroundBase
{
    protected static final int NUMBER_OF_PILES = 13;
    protected static final int NUMBER_OF_LINKED_PILES = 7;

    // Aktualne vybrana karta alebo list kariet
    protected ListOfCards actualList;
    // Aktualny solitaire.pile z ktoreho bola karta zobrata
    protected Pile actualPile;

    // Vsetky decky potrebne pre hranie
    protected DeckPile deckPile;
    protected DrawPile drawPile;
    protected DrawHelpPile drawHelpPile;
    protected ArrayList<DiscardPile> discardPiles;    // 4 decks
    protected ArrayList<LinkedPile> linkedPiles;      // 8 decks

    // Pole vsetkych Pile-ov
    // 0 - Draw_Pile
    // 1-7 - Linked_Pile
    // 8-11 - Discard_Pile
    // 12 - Draw_Help_Pile
    protected Pile[] allPiles;
    // List undo commandov
    protected ArrayList<Command> undoList;
}