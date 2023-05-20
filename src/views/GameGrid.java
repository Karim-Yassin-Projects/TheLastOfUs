package views;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Game;
import engine.GameListener;
import model.world.Cell;
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
                add(button);
                updateCellButton(i, j);                
            }
        }

        Game.addGameListener(new GameListener() {
            @Override
            public void onCellChanged(int x, int y, Cell oldCell, Cell newCell) {
                updateCellButton(14-x, y);
            }
        });
    }

    private void updateCellButton(int i, int j) {
        JButton button = (JButton)getComponent(i * 15 + j);
        if (Game.map[14 - i][j].isVisible()) {
            button.setBackground(Color.WHITE);
        } else {
            button.setBackground(Color.DARK_GRAY);
        }
        if (Game.map[14 - i][j] instanceof CollectibleCell) {
            CollectibleCell cell = (CollectibleCell) Game.map[14 - i][j];
            Icon icon = new ImageIcon(cell.getCollectible().getImage());
            button.setIcon(icon);
        } else if (Game.map[14 - i][j] instanceof CharacterCell) {
            CharacterCell cell = (CharacterCell) Game.map[14 - i][j];
            if (cell.getCharacter() != null) {
                Icon icon = new ImageIcon(cell.getCharacter().getImage());
                button.setIcon(icon);
            }
        }
    }
}
