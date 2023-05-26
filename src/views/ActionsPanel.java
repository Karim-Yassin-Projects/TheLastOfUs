package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import engine.Game;
import engine.GameListener;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Hero;

public class ActionsPanel extends JPanel {
    private ActionPanelButton attackButton;
    private ActionPanelButton cureButton;
    private ActionPanelButton useSpecialButton;
    private ActionPanelButton endTurnButton;

    public ActionsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.BLACK);
        attackButton = new ActionPanelButton();
        attackButton.setBackground(Color.WHITE);
        attackButton.setText("Attack");
        this.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        attackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Game.getSelectedHero() == null) {
                    return;
                }
                try {
                    Game.getSelectedHero().attack();
                } catch (NotEnoughActionsException | InvalidTargetException e1) {
                    GameView.handleError(e1);
                }

            }
        });

        cureButton = new ActionPanelButton();
        cureButton.setBackground(Color.WHITE);
        cureButton.setText("Cure");
        cureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Game.getSelectedHero() == null) {
                    return;
                }
                try {
                    Game.getSelectedHero().cure();
                } catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e1) {
                    GameView.handleError(e1);
                }

            }
        });

        useSpecialButton = new ActionPanelButton();
        useSpecialButton.setText("Use Special Action");
        useSpecialButton.setBackground(Color.WHITE);

        useSpecialButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Game.getSelectedHero() == null) {
                    return;
                }
                try {
                    Game.getSelectedHero().useSpecial();
                } catch (NoAvailableResourcesException | InvalidTargetException e1) {
                    GameView.handleError(e1);
                }

            }
        });

        endTurnButton = new ActionPanelButton();
        endTurnButton.setText("End Turn");
        endTurnButton.setBackground(Color.WHITE);
        endTurnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Game.endTurn();
                } catch (NotEnoughActionsException | InvalidTargetException e1) {
                    GameView.handleError(e1);
                }
            }
        });

        this.add(endTurnButton);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize( new Dimension(10, 0) );
        this.add(separator);
        this.add(attackButton);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize( new Dimension(10, 0) );
        this.add(separator);
        this.add(cureButton);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize( new Dimension(10, 0) );
        this.add(separator);
        this.add(useSpecialButton);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize( new Dimension(10, 0) );
        this.add(separator);
        enableOrDisableButtons();
        Game.addGameListener(new GameListener() {
            @Override
            public void onSelectedHeroChange(Hero oldHero, Hero newHero) {
                enableOrDisableButtons();
            }
        });
    }

    public void enableOrDisableButtons() {
        boolean heroIsSelected = Game.getSelectedHero() != null;
        attackButton.setEnabled(heroIsSelected);
        cureButton.setEnabled(heroIsSelected);
        useSpecialButton.setEnabled(heroIsSelected);
    }
}
