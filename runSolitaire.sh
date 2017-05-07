if [ "$#" -eq 0 ]; then
    rm -f src/*.class src/solitaire.pile/*.class src/solitaire.card/*.class src/solitaire.gui/*.class
    javac src/solitaire.solitaire.SolitaireClient.java src/GameController.java src/Command.java src/PlayGround.java src/solitaire.pile/Pile.java src/solitaire.pile/DiscardPile.java src/solitaire.pile/DrawPile.java src/solitaire.pile/DrawHelpPile.java src/solitaire.pile/DeckPile.java src/solitaire.card/ListOfCards.java src/solitaire.card/solitaire.Card.java src/solitaire.gui/ControlPanel.java src/solitaire.gui/Button.java src/solitaire.gui/ButtonHint.java
    java src.solitaire.solitaire.SolitaireClient
else
    if [ "$#" -eq 1 ] && [ "$1" == "clean" ]; then
        rm -f src/*.class src/solitaire.pile/*.class src/solitaire.card/*.class src/solitaire.gui/*.class
    else
        echo "./runSolitaire.sh [clean]"
    fi
fi
