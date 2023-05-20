package views;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Game;
import model.characters.Hero;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class GameGrid extends JPanel {
    public GameGrid() {
        super();
        this.setLayout(new GridLayout(15, 15));
        this.setBackground(Color.DARK_GRAY);
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton();
                if (Game.map[14 - i][j].isVisible()) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.DARK_GRAY);
                }
                if (Game.map[14 - i][j] instanceof CollectibleCell) {
                    CollectibleCell cell = (CollectibleCell) Game.map[14 - i][j];
                    if (cell.getCollectible() instanceof Vaccine) {
                        Icon icon = new ImageIcon("images/vaccine.png");
                        button.setIcon(icon);
                    } else {
                        Icon icon = new ImageIcon("images/supply.png");
                        button.setIcon(icon);
                    }

                } else if (Game.map[14 - i][j] instanceof CharacterCell) {
                    CharacterCell cell = (CharacterCell) Game.map[14 - i][j];
                    if (cell.getCharacter() instanceof Zombie) {
                        Icon icon = new ImageIcon("images/zombie.png");
                        button.setIcon(icon);
                    } else if (cell.getCharacter() instanceof Hero) {
                        Icon icon = new ImageIcon(cell.getCharacter().getImage());
                        button.setIcon(icon);
                    }
                }
                add(button);
            }
        }
    }
}
