package views;

import java.awt.BorderLayout;

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

public class GameMainView extends JPanel {
    private JPanel map;
    private SidePanel sidePanel;
    private ActionsPanel actionsPanel;
    public GameMainView() {
        super();
        this.setLayout(new BorderLayout());
        actionsPanel = new ActionsPanel();
        this.add(actionsPanel, BorderLayout.NORTH);
        
        map = new JPanel();
        map.setLayout(new GridLayout(15, 15));
        map.setBackground(Color.DARK_GRAY);
        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

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
                        Icon icon = new ImageIcon("C:\\Projects\\CSEN401\\TheLastOfUs\\images\\vaccine.png");
                        button.setIcon(icon);
                    } else {
                        Icon icon = new ImageIcon("C:\\Projects\\CSEN401\\TheLastOfUs\\images\\supply.png");
                        button.setIcon(icon);
                    }

                } else if (Game.map[14 - i][j] instanceof CharacterCell) {
                    CharacterCell cell = (CharacterCell) Game.map[14 - i][j];
                    if (cell.getCharacter() instanceof Zombie) {
                        Icon icon = new ImageIcon("C:\\Projects\\CSEN401\\TheLastOfUs\\images\\zombie.png");
                        button.setIcon(icon);
                    } else if (cell.getCharacter() instanceof Hero) {
                        Icon icon = new ImageIcon(cell.getCharacter().getImage());
                        button.setIcon(icon);
                    }
                }

                map.add(button);

            }

        }
        add(map, BorderLayout.CENTER);

    }

}
