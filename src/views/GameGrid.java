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
        this.setLayout(new GridLayout(Game.GRID_HEIGHT, Game.GRID_WIDTH));
        this.setBackground(Color.DARK_GRAY);
        
        for (int i = 0; i < Game.GRID_HEIGHT; i++) {
            for (int j = 0; j < Game.GRID_WIDTH; j++) {
                JButton button = new JButton();
                add(button);
                updateCellButton(i, j);                
            }
        }

        Game.addGameListener(new GameListener() {
            @Override
            public void onCellChanged(int x, int y, Cell oldCell, Cell newCell) {
                updateCellButton(Game.GRID_HEIGHT - 1 -x, y);
            }
        });
    }

    private void updateCellButton(int i, int j) {
        JButton button = (JButton)getComponent(i * Game.GRID_WIDTH + j);
        int mapI = Game.GRID_HEIGHT - 1 - i;
        if (Game.map[mapI][j].isVisible()) {
            button.setBackground(Color.WHITE);
        } else {
            button.setBackground(Color.DARK_GRAY);
        }
        if (Game.map[mapI][j] instanceof CollectibleCell) {
            CollectibleCell cell = (CollectibleCell) Game.map[mapI][j];
            Icon icon = new ImageIcon(cell.getCollectible().getImage());
            button.setIcon(icon);
        } else if (Game.map[mapI][j] instanceof CharacterCell) {
            CharacterCell cell = (CharacterCell) Game.map[mapI][j];
            if (cell.getCharacter() != null) {
                Icon icon = new ImageIcon(cell.getCharacter().getImage());
                button.setIcon(icon);
            }
        }
    }
}
