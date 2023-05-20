package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import model.characters.*;

import engine.Game;

public class HeroSelection extends JPanel {
    private ArrayList<HeroSelectionListener> listeners = new ArrayList<>();

    public HeroSelection() throws IOException {
        super();
        Game.loadHeroes("C:\\Projects\\CSEN401\\Milestone2-Solution\\Heroes.csv");
        this.setLayout(new GridLayout(Game.availableHeroes.size(), 1));
        for (Hero h : Game.availableHeroes) {
            Icon icon = new ImageIcon(h.getImage(), h.getName());
            JButton button = new JButton(h.getHtmlDescription(), icon);
            button.setPreferredSize(new Dimension(400, 60));
            button.setMaximumSize(button.getPreferredSize());
            this.add(button);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        selectHero(h);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

    }

    public void addHeroSelectionListener(HeroSelectionListener listener) {
        this.listeners.add(listener);
    }

    public void removeHeroSelectionListener(HeroSelectionListener listener) {
        this.listeners.remove(listener);
    }

    private void selectHero(Hero hero) throws IOException {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to selected the hero " + hero.getName()
                        + "? Once selected, the game will start, and you will not be able to change hero.",
                "Confirm Hero Selection", JOptionPane.YES_NO_OPTION);
        if (result != JOptionPane.YES_OPTION) {
            return;
        }
        for (HeroSelectionListener l : listeners) {
            l.heroSelected(hero);
        }
    }
}
