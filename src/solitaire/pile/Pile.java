package solitaire.pile;

import java.awt.Graphics;

import java.util.ArrayList;

import solitaire.card.*;
import solitaire.PlayGround;


/**
 * Trieda z ktorej dedia vsetky ostatne xxxPile
 */
public abstract class Pile
{
	protected ArrayList<Card> cardList;

	public Pile()
	{
		this.cardList = new ArrayList<Card>();
	}

	/**
	 * Prida kartu na koniec balicku
	 * a vola setDefaultPosition pre danu kartu
	 */
	public void addCard(Card inputCard)
	{
		this.cardList.add(inputCard);
	}

	/**
	 * Vola sa pri undo, maze karty z solitaire.pile-u
	 * VELMI, VELMI NEBEZPECNA funkcia !!!!!
	 * Nekontroluje sa ci sa maze spravna karta !!!!!
	 * Maze podla poctu alebo len vrchnu kartu a pod..
	 */
	public void removeCard(ListOfCards inputList) {}

	/**
	 * Do balicku kariet v danej Pile-e ulozi vstupny zoznam kariet
	 */
	public void returnListOfCardsToPile(ListOfCards inputList, boolean action) {}

	/**
	 * Vola sa len pri linkedPile
	 */
	public boolean actionEnded() { return false; }
}
