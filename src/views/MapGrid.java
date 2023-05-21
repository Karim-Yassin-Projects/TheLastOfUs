package views;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Game;
import engine.GameListener;
import model.characters.Character;
import model.characters.Hero;
import model.characters.Zombie;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;

public class MapGrid extends JPanel {
    public MapGrid() {
        super();
        this.setLayout(new GridLayout(15, 15));
        this.setBackground(Color.GRAY);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton button = new JButton();
                int x = i;
                int y = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleClick(x, y);
                    }
                });
                add(button);
                updateCell(i, j);
            }

        }
        Game.addGameListener(new GameListener() {
            @Override
            public void onCellChanged(int i, int j, Cell oldCell, Cell newCell) {
                updateCell(14 - i, j);
            }

            @Override
            public void onSelectedHeroChange(Hero oldHero, Hero newHero) {
                if (oldHero != null) {
                    Point loc = oldHero.getLocation();
                    updateCell(14 - loc.x, loc.y);
                }
                if (newHero != null) {
                    Point loc = newHero.getLocation();
                    updateCell(14 - loc.x, loc.y);
                }
            }
        });
        
    }

    public void handleClick(int i, int j) {
        Cell cell = Game.map[14 - i][j];
        if (cell instanceof CharacterCell) {
            CharacterCell characterCell = (CharacterCell) cell;
            Character c = characterCell.getCharacter();
            if (c instanceof Hero) {
                Hero hero = (Hero) c;
                if (Game.getSelectedHero() == hero) {
                    Game.setSelectedHero(null);
                } else {
                    Game.setSelectedHero(hero);
                }
            } else if (c instanceof Zombie) {
                Zombie z = (Zombie) c;
                if (Game.getSelectedHero() == null) {
                    return;
                } else {
                    Game.getSelectedHero().setTarget(z);
                }

            }
        }

    }

    public void updateCell(int i, int j) {
        JButton button = (JButton) getComponent(i * 15 + j);
        Cell cell = Game.map[14 - i][j];
        updateCellVisibility(button, cell);
        if (cell instanceof CollectibleCell) {
            CollectibleCell colCell = (CollectibleCell) cell;
            updateCollectibleCell(button, colCell);
            setButtonBorder(button, Color.BLACK);
        } else if (cell instanceof CharacterCell) {
            CharacterCell charCell = (CharacterCell) cell;
            updateCharacterCell(button, charCell);
        } else {
            setButtonBorder(button, Color.BLACK);
        }
    }

    private static void setButtonBorder(JButton button, Color color) {
        button.setBorder(BorderFactory.createLineBorder(color, 1));
    }

    private static void updateCharacterCell(JButton button, CharacterCell charCell) {
        Character character = charCell.getCharacter();
        if (character != null) {
            Icon icon = new ImageIcon(character.getImage());
            button.setIcon(icon);
            boolean isSelected = Game.getSelectedHero() == character;
            boolean isTarget = Game.getSelectedHero() != null && character == Game.getSelectedHero().getTarget();

            if (isSelected && isTarget) {
                setButtonBorder(button, new Color(255, 0, 255, 255));
            } else if (isSelected) {
                setButtonBorder(button, Color.BLUE);
            } else if (isTarget) {
                setButtonBorder(button, Color.RED);
            } else {
                setButtonBorder(button, Color.BLACK);
            }
        } else {
            button.setIcon(new ImageIcon());
            setButtonBorder(button, Color.BLACK);
        }
    }

    private static void updateCollectibleCell(JButton button, CollectibleCell colCell) {
        Icon icon = new ImageIcon(colCell.getCollectible().getImage());
        button.setIcon(icon);
    }

    private static void updateCellVisibility(JButton button, Cell cell) {
        if (cell.isVisible()) {
            button.setBackground(Color.WHITE);
        } else {
            button.setBackground(Color.DARK_GRAY);
        }
    }
}
