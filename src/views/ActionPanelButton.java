package views;

import javax.swing.JButton;
import javax.swing.plaf.metal.MetalButtonUI;

public class ActionPanelButton extends JButton {



    public ActionPanelButton(String text) {
        this();
        this.setText(text);
    }

    public ActionPanelButton() {
        super();

        this.setUI(new MetalButtonUI());
        this.setFocusable(false);
    }
}
