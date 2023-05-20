package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GameMainView extends JPanel {
    private JPanel map;
    private SidePanel sidePanel;
    private ActionsPanel actionsPanel;
    public GameMainView() {
        super();
        this.setLayout(new BorderLayout());
        actionsPanel = new ActionsPanel();
        this.add(actionsPanel, BorderLayout.NORTH);
        
        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);
        
        map = new GameGrid();
        add(map, BorderLayout.CENTER);
    }

}
