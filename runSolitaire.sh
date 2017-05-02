if [ "$#" -eq 0 ]; then
    rm -f src/*.class src/pile/*.class src/card/*.class src/gui/*.class
    javac src/Solitaire.java src/GameController.java src/Command.java src/PlayGround.java src/pile/Pile.java src/pile/DiscardPile.java src/pile/DrawPile.java src/pile/DrawHelpPile.java src/pile/DeckPile.java src/card/ListOfCards.java src/card/Card.java src/gui/ControlPanel.java src/gui/Button.java src/gui/ButtonHint.java
    java src.Solitaire
else
    if [ "$#" -eq 1 ] && [ "$1" == "clean" ]; then
        rm -f src/*.class src/pile/*.class src/card/*.class src/gui/*.class
    else
        echo "./runSolitaire.sh [clean]"
    fi
fi
