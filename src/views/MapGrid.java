package views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Game;
import model.characters.Character;
import model.characters.Hero;
import model.characters.Zombie;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class MapGrid extends JPanel {
    private static ArrayList<JButton> buttons = new ArrayList<>();
    public MapGrid() {
        super();
        this.setLayout(new GridLayout(15, 15));
        this.setBackground(Color.GRAY);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button =  new JButton();
                buttons.add(button);
                int x = i;
                int y = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(x,y);
                    }
                });
                add(button);
                updateCell(i, j);
            }

        }
    }
    public void handleClick(int i, int j){
        Cell cell = Game.map[14-i][j];
        if(cell instanceof CharacterCell){
            CharacterCell characterCell = (CharacterCell)cell;
            Character c = characterCell.getCharacter();
            if(c instanceof Hero){
                Hero hero = (Hero)c;
                if(Game.getSelectedHero() == hero){
                    Game.setSelectedHero(null);
                }
                else{
                    Game.setSelectedHero(hero);
                }
            }
            else if(c instanceof Zombie){
                Zombie z = (Zombie)c;
                if(Game.getSelectedHero() == null){
                    return;
                }
                else{
                    Game.getSelectedHero().setTarget(z);
                }

            }
            else{
                return;
            }
        }
        
    }

    public static void updateCell(int i, int j) {
        JButton button = buttons.get(i*15 + j);
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
            else{
                button.setIcon(new ImageIcon());
            }
        }

    }
}
