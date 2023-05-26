package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import model.characters.Hero;

public class GameView extends JFrame {
    private GameMainView gameMainView;
    private HeroSelection heroSelection;
    private SoundPlayer backgroundMusic;
    private static boolean enableMusic = true;
    private Splash splash;
    private Timer splashTimer;

    public GameView(boolean showSplash) {
        super();
        backgroundMusic = new SoundPlayer("sounds/theme.wav", true);
        if (enableMusic) {
            backgroundMusic.start();
        }
        new SoundEffects();
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
        setupKeyboardActions();
    }

    private void setupKeyboardActions() {
        JPanel container = (JPanel)getContentPane();
        ActionMap actionMap =  container.getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = container.getInputMap(condition);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0, true), "music");

        actionMap.put("music", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enableMusic) {
                    enableMusic = false;
                    if (backgroundMusic != null) {
                        backgroundMusic.stop();
                    }
                } else {
                    enableMusic = true;
                    if (backgroundMusic != null) {
                        backgroundMusic.start();
                    }
                }
                
            }
        });
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
                if (enableMusic) {
                        backgroundMusic.start();
                }
                int result = JOptionPane.showConfirmDialog(gameOverPanel, "Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
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

    public static void handleError(Exception e) {
        String message = e.getMessage();
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE, null);
        SoundEffects.playErrorSound();
    }

    public static void main(String[] args) {
        new GameView(true);
    }

    
}
