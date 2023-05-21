package views;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class ActionsPanel extends JPanel{
    public ActionsPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.BLACK);
        JButton attack = new JButton();
        attack.setBackground(Color.WHITE);
        attack.setText("Attack");
        attack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    Game.getSelectedHero().attack();
                }
                catch(InvalidTargetException s){
                    s.getMessage();
                } catch (NotEnoughActionsException e1) {
                    e1.getMessage();
                }
                
            }
        });
        JButton cure = new JButton();
        cure.setBackground(Color.WHITE);
        cure .setText("Cure");
        cure.addMouseListener(new MouseAdapter() {
            try{
                Game.getSelectedHero().cure();
            }
            catch(InvalidTargetException e1){
                e1.getMessage();
            }
        });

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
