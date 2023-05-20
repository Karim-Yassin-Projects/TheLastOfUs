package views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HealthBar extends JPanel {

    private int currentHp;
    private final int maxHp;
    JLabel healthLabel;

    public HealthBar(int currentHealth, int maxHealth) {
        super();
        this.currentHp = currentHealth;
        this.maxHp = maxHealth;

        this.healthLabel = new JLabel();
        this.healthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.healthLabel.setOpaque(true);
        this.healthLabel.setBackground(new Color(0, 0, 0, 0));
        this.add(healthLabel);

        this.setOpaque(false);
        updateHealthLabel();
    }

    private void updateHealthLabel() {
        int healthPercent = (int) ((double) currentHp / maxHp * 100);
        if (currentHp == 0) {
            healthLabel.setText("DEAD");
        } else {
            healthLabel.setText(currentHp + " / " + maxHp + " (" + healthPercent + "%)");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width, height);

        double healthPercent = (double) currentHp / maxHp;
        int max = width - 2;
        int barWidth = (int) (healthPercent * max);

        Color barColor;
        if (healthPercent > 0.8) {
            barColor = Color.GREEN;
        } else if (healthPercent > 0.3) {
            barColor = Color.ORANGE;
        } else {
            barColor = Color.red;
        }

        g.setColor(barColor);
        g.fillRect(1, 1, barWidth, height - 2);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHealth) {
        this.currentHp = currentHealth;
        this.updateHealthLabel();
        this.repaint();
    }
}
