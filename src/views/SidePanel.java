package views;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import engine.Game;
import engine.GameListener;
import model.characters.Hero;

public class SidePanel extends JPanel {
    private HeroStatsPanel heroStatsPanel;

    public SidePanel() {
        super();
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        for (Hero hero : Game.heroes) {
            heroStatsPanel = new HeroStatsPanel(hero);
            add(heroStatsPanel);
        }
        Game.addGameListener(new GameListener() {
            @Override
            public void onHeroAdded(Hero hero) {
                Game.addHero(hero);
                add(new HeroStatsPanel(hero));
            }

            @Override
            public void onHeroRemoved(Hero hero) {
                Game.removeHero(hero);
                for (Component component : getComponents()) {
                    if (component instanceof HeroStatsPanel) {
                        HeroStatsPanel heroStatsPanel = (HeroStatsPanel) component;
                        if (heroStatsPanel.getHero() == hero) {
                            remove(component);
                        }
                    }
                }
            }
        });

    }
}
