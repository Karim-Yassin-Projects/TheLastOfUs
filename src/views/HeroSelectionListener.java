package views;

import java.io.IOException;

import model.characters.Hero;

public interface HeroSelectionListener {
    void heroSelected(Hero hero) throws IOException;
}
