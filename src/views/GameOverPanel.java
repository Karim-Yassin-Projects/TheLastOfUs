package views;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;



public class GameOverPanel extends JPanel{
    private JLabel label;
    public GameOverPanel() {
        super();
        this.setLayout(new BorderLayout());
        label = new JLabel();
        ImageIcon imageIcon = new ImageIcon(Game.checkWin() ? "images/victory.png":"images/defeat.png");
        
        label.setIcon(imageIcon);
        this.add(label, BorderLayout.CENTER);
    }
}