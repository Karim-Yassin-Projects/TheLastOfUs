package views;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import engine.Game;
import model.characters.Hero;

public class GameView extends JFrame {
    private GameMainView gameMainView;

    public GameView() throws IOException {
        super();
        this.setLocation(100, 100);
        this.setMinimumSize(new Dimension(1280, 720));
        this.setSize(getMinimumSize());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("The Last of Us Legacy");
        HeroSelection heroSelection = new HeroSelection();
        heroSelection.addHeroSelectionListener(new HeroSelectionListener() {
            public void heroSelected(Hero h) throws IOException {
                Game.startGame(h);
                heroSelection.setVisible(false);
                remove(heroSelection);

                gameMainView = new GameMainView();

                add(gameMainView);
            }
        });
        this.add(heroSelection);
        this.setVisible(true);
        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        
                    }
                    int mw = getMinimumSize().width;
                    int mh = getMinimumSize().height;
                    int w = getWidth();
                    int h = getHeight();
                    if (w < mw || h < mh) {
                        w = Math.max(w, mw);
                        h = Math.max(h, mh);
                        setSize(w, h);
                    }
                });
                
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new GameView();
    }
}
