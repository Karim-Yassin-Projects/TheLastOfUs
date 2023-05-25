package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.characters.Character;
import model.characters.CharacterListener;
import model.characters.Hero;

public class HeroStatsPanel extends JPanel {
    private HealthBar healthBar;
    private JLabel statusLabel;
    private Hero hero;
    private JPanel innerPanel;

    public HeroStatsPanel(Hero hero) {
        super();
        innerPanel = new JPanel();
        add(innerPanel);

        innerPanel.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(150, 220));

        this.setMaximumSize(getPreferredSize());
        this.setBackground(Color.WHITE);
        innerPanel.setBackground(Color.WHITE);

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        innerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel label;
        try {
            BufferedImage image = ImageIO.read(new File(hero.getImage()));
            label = new JLabel(new ImageIcon(image));

        } catch (IOException e) {
            label = new JLabel(hero.getName());
        }

        innerPanel.add(label, BorderLayout.CENTER);
        statusLabel = new JLabel();
        statusLabel.setText(hero.getHtmlDescriptionInGame());
        innerPanel.add(statusLabel, BorderLayout.SOUTH);

        healthBar = new HealthBar(hero.getCurrentHp(), hero.getMaxHp());
        innerPanel.add(healthBar, BorderLayout.NORTH);

        hero.addCharacterListener(new CharacterListener() {
            @Override
            public void onChangedProperty(Character character, String propertyName, int oldValue, int newValue) {
                if (propertyName.equals("currentHp")) {
                    healthBar.setCurrentHp(newValue);
                } else {
                    statusLabel.setText(hero.getHtmlDescriptionInGame());
                }
            }
        });
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
