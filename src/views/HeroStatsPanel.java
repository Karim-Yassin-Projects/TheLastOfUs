package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.characters.Character;
import model.characters.CharacterListener;
import model.characters.Hero;

public class HeroStatsPanel extends JPanel {
    private HealthBar healthBar;
    private JLabel statusLabel;
    public HeroStatsPanel(Hero hero) {
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(150, 150));
        this.setMaximumSize(getPreferredSize());
        this.setBackground(Color.WHITE);

        JLabel label;
        try {
            BufferedImage image = ImageIO.read(new File(hero.getImage()));
            label = new JLabel(new ImageIcon(image));
            
        } catch (IOException e) {
            label = new JLabel(hero.getName());
        }

        this.add(label, BorderLayout.CENTER);
        statusLabel = new JLabel();
        statusLabel.setText(hero.getHtmlDescriptionInGame());
        this.add(statusLabel, BorderLayout.SOUTH);

        healthBar = new HealthBar(hero.getCurrentHp(), hero.getMaxHp());
        add(healthBar, BorderLayout.NORTH);

        hero.addCharacterListener(new CharacterListener() {
            @Override
            public void onChangedProperty(Character character, String propertyName, int oldValue, int newValue) {
                if(propertyName.equals("currentHp")){
                    healthBar.setCurrentHp(newValue);
                }
                else{
                    statusLabel.setText(hero.getHtmlDescriptionInGame());
                }
            }
        });
    }
}
