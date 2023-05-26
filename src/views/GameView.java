package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import model.characters.Hero;
import model.world.Cell;

public class GameView extends JFrame {
    
    private SoundPlayer backgroundMusic;
    public SoundPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    public void setBackgroundMusic(SoundPlayer backgroundMusic) {
        if (this.backgroundMusic != null) {
            this.backgroundMusic.stop();
        }
        this.backgroundMusic = backgroundMusic;
    }

    private boolean enableMusic = true;
    
    private GameMainView gameMainView;
    private HeroSelection heroSelection;
    private Splash splash;
    private Timer splashTimer;
    public static GameView mainWindow;
    private JLabel errorLabel;

    public GameView(boolean showSplash) {
        super();
        getContentPane().setLayout(new BorderLayout());
        stopMusic();
        setBackgroundMusic(new SoundPlayer("sounds/theme.wav", true));
        new SoundEffects();
        this.setLocation(100, 100);
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("The Last of Us Legacy");
        if (showSplash) {
            this.splash = new Splash();
            this.getContentPane().add(splash);
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

        this.errorLabel = new JLabel();
        this.errorLabel.setOpaque(true);
        this.errorLabel.setBackground(Color.RED);
        this.errorLabel.setForeground(Color.WHITE);
        this.errorLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        this.errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.errorLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        this.errorLabel.setVisible(false);
        this.getContentPane().add(this.errorLabel, BorderLayout.SOUTH);
    }

    private void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    private void startMusic() {
        if (backgroundMusic != null && enableMusic) {
            backgroundMusic.start();
        }
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
                    stopMusic();
                } else {
                    enableMusic = true;
                    startMusic();
                }
                
            }

            
        });
    }

    private void setupGameListener() {
        Game.addGameListener(new GameListener() {
            @Override
            public boolean onGameOver() {
                GameOverPanel gameOverPanel = new GameOverPanel();
                getContentPane().remove(gameMainView);
                getContentPane().add(gameOverPanel);
                stopMusic();
                setBackgroundMusic(new SoundPlayer(
                        Game.checkWin() ? "sounds/victory.wav" : "sounds/defeat.wav", true));
                startMusic();
                int result = JOptionPane.showConfirmDialog(gameOverPanel, "Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
                stopMusic();
                if (result != JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                    return false;
                } else {
                    Game.resetGame();
                    // setVisible(false);
                    // new GameView(false);
                    try {
                        showHeroSelection();
                        setupGameListener();
                        setBackgroundMusic(new SoundPlayer("sounds/theme.wav", true));
                        startMusic();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
            }

            @Override
            public void onTrapCell(Cell cell) {
                showErrorMessage("ATTENTION! Your hero just entered a trap cell.", Color.ORANGE);
            }
        });
    }

    private void showHeroSelection() throws IOException {
        int n = 0;
        while (getContentPane().getComponentCount() > n) {
            Component c = getContentPane().getComponent(n);
            if (c == errorLabel) {
                n++;
                continue;
            }
            c.setVisible(false);
            getContentPane().remove(c);
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
        this.getContentPane().add(heroSelection);
    }

    Timer errorTimer;
    public static void handleError(Exception e) {
        String message = e.getMessage();
        showErrorMessage(message, Color.RED);

        SoundEffects.playErrorSound();
    }

    private static void showErrorMessage(String message, Color color) {
        mainWindow.errorLabel.setText(message);
        mainWindow.errorLabel.setBackground(color);
        mainWindow.errorLabel.setVisible(true);;
        mainWindow.getContentPane().invalidate();

        if (mainWindow.errorTimer != null) {
            mainWindow.errorTimer.stop();
        }

        mainWindow.errorTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.errorLabel.setVisible(false);
                if (mainWindow.errorTimer != null) {
                    mainWindow.errorTimer.stop();
                    mainWindow.errorTimer = null;
                }
            }
        });
        mainWindow.errorTimer.start();
    }

    public static void main(String[] args) {
        mainWindow = new GameView(true);
    }
}
