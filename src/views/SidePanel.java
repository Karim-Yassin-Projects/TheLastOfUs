package views;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import engine.Game;
import model.characters.Hero;

public class SidePanel extends JPanel {
    private HeroStatsPanel heroStatsPanel;

    public SidePanel() {
        super();
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Hero hero : Game.heroes) {
            HeroStatsPanel heroStatsPanel = new HeroStatsPanel(hero);
            add(heroStatsPanel);
        }
    }
}
