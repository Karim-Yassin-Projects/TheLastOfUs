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
    GameView that = this;
    // public void checkGameCondition () throws IOException {
    //     if(Game.checkGameOver()) {
    //             JOptionPane jOptionPane = new JOptionPane();
    //                  if(Game.checkWin()) {
    //                 jOptionPane.showMessageDialog(this,"YOU WIN! Would you like to play again?", "Victory Message", JOptionPane.YES_NO_OPTION);
                        
    //                  }
    //                  else {
    //                 jOptionPane.showMessageDialog(this,"YOU LOSE! Would you like to play again?", "Defeat Message", JOptionPane.YES_NO_OPTION);
    //                  }
    //                 if(jOptionPane.getMessageType() == JOptionPane.YES_OPTION){
    //                     this.remove(gameMainView);
    //                     this.add(new HeroSelection());
    //                     this.setVisible(true);
    //                 }
    //                 else{
    //                     this.dispose();
    //                 }
    //             }
    // }

    

    public static void main(String[] args) throws IOException {
        new GameView();
    }
}
