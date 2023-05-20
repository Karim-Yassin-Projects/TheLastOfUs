package views;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionsPanel extends JPanel{
    public ActionsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.BLACK);
        JButton attack = new JButton();
        attack.setBackground(Color.WHITE);
        attack.setText("Attack");
        JButton cure = new JButton();
        cure.setBackground(Color.WHITE);
        cure .setText("Cure");
        JButton useSpecial = new JButton();
        useSpecial.setText("Use Special Action");
        useSpecial.setBackground(Color.WHITE);
        JButton endTurn = new JButton();
        endTurn.setText("End Turn");
        endTurn.setBackground(Color.WHITE);
        this.add(endTurn);
        this.add(attack);
        this.add(cure);
        this.add(useSpecial);
    }
}
