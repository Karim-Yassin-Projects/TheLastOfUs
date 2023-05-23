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
    private JButton attackButton;
    private JButton cureButton;
    private JButton useSpecialButton;
    private JButton endTurnButton;
    public ActionsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.BLACK);
        attackButton = new JButton();
        ActionsPanel that = this;
        attackButton.setBackground(Color.WHITE);
        attackButton.setText("Attack");
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.getSelectedHero().attack();
                } catch (NotEnoughActionsException | InvalidTargetException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog(that, message, "Attack Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });
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
       
        useSpecialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.getSelectedHero().useSpecial();
                } catch (NoAvailableResourcesException | InvalidTargetException e1) {
                    String message = e1.getMessage();
                    JOptionPane.showMessageDialog(that, message, "Special Action Error", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        });


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
    }
    public void enableOrDisableButtons(){
        boolean heroIsSelected = Game.getSelectedHero() != null;
        attackButton.setEnabled(heroIsSelected);
        cureButton.setEnabled(heroIsSelected);
        useSpecialButton.setEnabled(heroIsSelected);
    }
}
