package views;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.Game;
import engine.GameListener;
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
        Game.addGameListener(new GameListener() {
            @Override
            public boolean onGameOver() {
                GameOverPanel gameOverPanel = new GameOverPanel();
                remove(gameMainView);
                add(gameOverPanel);
                int result = JOptionPane.showConfirmDialog(gameOverPanel, "Would you like to play again?",
                        "Game Over Message", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    dispose();
                    return false;
                } else {
                    remove(gameOverPanel);
                    Game.resetGame();
                    add(heroSelection);
                    heroSelection.setVisible(true);
                    return true;
                }
            }
        });
        this.add(heroSelection);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new GameView();
    }
}
