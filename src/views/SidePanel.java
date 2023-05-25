package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import engine.Game;
import engine.GameListener;
import model.characters.Hero;

public class SidePanel extends JPanel {
    public JScrollPane scrollPane;
    public SidePanel() {
        super();
        scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        for (Hero hero : Game.heroes) {
            HeroStatsPanel heroStatsPanel = new HeroStatsPanel(hero);
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(170, getComponentCount() * 220);
    }
}
