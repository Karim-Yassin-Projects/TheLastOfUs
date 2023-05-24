package views;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ScaledButton extends JButton {

    private ImageIcon imageIcon;
    public ScaledButton() {
        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                setImageIcon(getImageIcon());
            }
        });
    }
    
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setIcon(getResizedIcon(imageIcon));
    }

    ImageIcon getImageIcon() {
        return imageIcon;
    }

    ImageIcon getResizedIcon(ImageIcon icon) {
        if (icon == null) {
            return null;
        }
        Image img = icon.getImage();
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();

        int buttonHeight = getHeight() - 10;
        if (buttonHeight <= 0) {
            return icon;
        }
        if (imageHeight > buttonHeight) {
            int newWidth = buttonHeight * imageWidth / imageHeight;
            int newHeight = buttonHeight;
            img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return icon;
    }
}
