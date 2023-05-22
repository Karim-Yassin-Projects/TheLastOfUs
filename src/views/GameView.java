package views;

import java.io.IOException;

import javax.swing.JFrame;
import engine.Game;
import model.characters.Hero;

public class GameView extends JFrame {
    private GameMainView gameMainView;

    public GameView() throws IOException {
        super();
        this.setLocation(100, 100);
        this.setSize(1280, 720);
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

    }

    

    public static void main(String[] args) throws IOException {
        new GameView();
    }
}
