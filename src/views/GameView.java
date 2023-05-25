package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import model.characters.Hero;

public class GameView extends JFrame {
    private GameMainView gameMainView;
    private HeroSelection heroSelection;
    private SoundPlayer backgroundMusic;
    private Splash splash;
    private Timer splashTimer;

    public GameView(boolean showSplash) {
        super();
        backgroundMusic = new SoundPlayer("sounds/theme.wav", true);
        backgroundMusic.start();
        this.setLocation(100, 100);
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("The Last of Us Legacy");
        if (showSplash) {
            this.splash = new Splash();
            this.add(splash);

            splashTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        showHeroSelection();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    splashTimer.stop();
                }
            });
            splashTimer.start();
        } else {
            try {
                showHeroSelection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setupGameListener();
        this.setVisible(true);

    }

    private void setupGameListener() {
        Game.addGameListener(new GameListener() {
            @Override
            public boolean onGameOver() {
                GameOverPanel gameOverPanel = new GameOverPanel();
                remove(gameMainView);
                add(gameOverPanel);
                backgroundMusic.stop();
                backgroundMusic = new SoundPlayer(
                        Game.checkWin() ? "sounds/victory.wav" : "sounds/defeat.wav", true);
                backgroundMusic.start();
                int result = JOptionPane.showConfirmDialog(gameOverPanel, "Would you like to play again?",
                        "Game Over Message", JOptionPane.YES_NO_OPTION);
                backgroundMusic.stop();
                if (result != JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                    return false;
                } else {
                    Game.resetGame();
                    setVisible(false);
                    new GameView(false);
                    return true;
                }
            }
        });
    }

    private void showHeroSelection() throws IOException {
        if (this.splash != null) {
            this.splash.setVisible(false);
            this.remove(splash);
        }

        heroSelection = new HeroSelection();
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
    }

    public static void main(String[] args) {
        new GameView(true);
    }
}
