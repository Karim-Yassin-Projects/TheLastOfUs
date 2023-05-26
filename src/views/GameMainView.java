package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import engine.Game;
import engine.GameListener;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.world.Cell;

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
        add(sidePanel.scrollPane, BorderLayout.EAST);

        mapGrid = new MapGrid();
        add(mapGrid, BorderLayout.CENTER);

        setupKeyboardActions();
        GameMainView that = this;
        Game.addGameListener(new GameListener() {
            @Override
            public void onTrapCell(Cell cell) {
                JOptionPane.showMessageDialog(that, "Warning! You just entered a trap cell.", "Trap Cell Message",
                        JOptionPane.OK_OPTION);
            }
        });
    }

    private void setupKeyboardActions() {
        ActionMap actionMap = getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "left");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "right");

        actionMap.put("up", new MoveAction(this, Direction.UP));
        actionMap.put("down", new MoveAction(this, Direction.DOWN));
        actionMap.put("left", new MoveAction(this, Direction.LEFT));
        actionMap.put("right", new MoveAction(this, Direction.RIGHT));
    }

    private static class MoveAction extends AbstractAction {
        private final Direction direction;

        public MoveAction(GameMainView view, Direction direction) {
            super();
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Game.getSelectedHero() == null) {
                return;
            }
            try {
                Game.getSelectedHero().move(direction);

            } catch (MovementException | NotEnoughActionsException me) {
                GameView.handleError(me);
            }
        }
    }
}
