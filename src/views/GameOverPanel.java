package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Game;

public class GameOverPanel extends JPanel {
    private JLabel label;
    private final ImageIcon imageIcon;

    public GameOverPanel() {
        super();
        this.setLayout(new BorderLayout());
        label = new JLabel();
        imageIcon = new ImageIcon(Game.checkWin() ? "images/victory.png" : "images/defeat.png");
        updateImage();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateImage();
            }
        });
        this.add(label, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
    }

    private void updateImage() {
        Image img = imageIcon.getImage();
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();

        int width = getWidth();
        int height = getHeight();
        if (width == 0 || height == 0) {
            return;
        }

        int newWidth;
        int newHeight;

        if (width * imageHeight > height * imageWidth) {
            newWidth = width;
            newHeight = imageHeight * width / imageWidth;

        } else {
            newHeight = height;
            newWidth = imageWidth * height / imageHeight;
        }
        img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

}