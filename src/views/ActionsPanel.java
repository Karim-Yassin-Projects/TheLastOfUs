package views;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Game;
import engine.GameListener;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Hero;

public class ActionsPanel extends JPanel {
    private JButton endTurnButton;
    private JButton useSpecialButton;
    private JButton cureButton;
    private JButton attackButton;

    public ActionsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.BLACK);
        attackButton = new JButton();
        attackButton.setBackground(Color.WHITE);
        attackButton.setText("Attack");
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.getSelectedHero().attack();
                } catch (InvalidTargetException s) {
                    s.getMessage();
                } catch (NotEnoughActionsException e1) {
                    e1.getMessage();
                }

            }
        });
        ActionsPanel that = this;
        cureButton = new JButton();
        cureButton.setBackground(Color.WHITE);
        cureButton.setText("Cure");
        cureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.getSelectedHero().cure();
                } catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog(that, message, "Cure Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });

        useSpecialButton = new JButton();
        useSpecialButton.setText("Use Special Action");
        useSpecialButton.setBackground(Color.WHITE);

        endTurnButton = new JButton();
        endTurnButton.setText("End Turn");
        endTurnButton.setBackground(Color.WHITE);

        endTurnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.endTurn();
                } catch (NotEnoughActionsException | InvalidTargetException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog(that, message, "End Turn Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });

        this.add(endTurnButton);
        this.add(attackButton);
        this.add(cureButton);
        this.add(useSpecialButton);

        Game.addGameListener(new GameListener() {
            @Override
            public void onSelectedHeroChange(Hero oldHero, Hero newHero) {
                enableOrDisableButtons();
            }
        });
        enableOrDisableButtons();
    }

    private void enableOrDisableButtons() {
        boolean heroSelected = Game.getSelectedHero() != null;
        attackButton.setEnabled(heroSelected);
        cureButton.setEnabled(heroSelected);
        useSpecialButton.setEnabled(heroSelected);
    }
}
