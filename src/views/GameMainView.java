package views;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import engine.Game;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;

public class GameMainView extends JPanel {
    private SidePanel sidePanel;
    private ActionsPanel actionsPanel;
    private MapGrid mapGrid;

    public GameMainView() {
        super();
        this.setLayout(new BorderLayout());
        actionsPanel = new ActionsPanel();
        this.add(actionsPanel, BorderLayout.NORTH);

        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

        mapGrid = new MapGrid();
        add(mapGrid, BorderLayout.CENTER);
    }

    public void keyPressed(KeyEvent e) throws MovementException, NotEnoughActionsException {
        Point p = Game.getSelectedHero().getLocation();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                Game.getSelectedHero().move(Direction.UP);
                // MapGrid.updateCell(p.x,p.y);
                // MapGrid.updateCell(p.x+1, p.y);
                break;
            case KeyEvent.VK_DOWN:
                Game.getSelectedHero().move(Direction.DOWN);
                // MapGrid.updateCell(p.x,p.y);
                // MapGrid.updateCell(p.x-1, p.y);
                break;
            case KeyEvent.VK_LEFT:
                Game.getSelectedHero().move(Direction.LEFT);
                // MapGrid.updateCell(p.x,p.y);
                // MapGrid.updateCell(p.x,p.y-1);

                break;
            case KeyEvent.VK_RIGHT:
                Game.getSelectedHero().move(Direction.DOWN);
                // MapGrid.updateCell(p.x,p.y);
                // MapGrid.updateCell(p.x,p.y+1);
                break;
            default:
                // MapGrid.updateCell(p.x,p.y);
        }
    }
}
