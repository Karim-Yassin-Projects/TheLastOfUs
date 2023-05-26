package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import model.characters.Character;
import model.characters.Hero;
import model.collectibles.Collectible;
import model.world.Cell;

public class SoundEffects extends GameListener {

    public SoundEffects() {
        super();
        Game.addGameListener(this);
    }

    @Override
    public void onCollectiblePickUp(Collectible collectible) {
        playSound(collectible.getSound());
    }

    @Override
    public void onHeroAdded(Hero hero) {
        if (Game.heroes.size() == 1) {
            return;
        }
        SoundPlayer sp = new SoundPlayer(hero.getThanksSound(), false);
        sp.start();
    }

    @Override
    public void onHeroSpecial(Hero hero) {
        playSound(hero.getSpecialSound());
    }

    @Override
    public void onTrapCell(Cell cell) {
        playSound("sounds/trap.wav", 0);
        Hero selectedHero = Game.getSelectedHero();
        if (selectedHero != null) {
            onCharacterDamaged(selectedHero, 500);
        }
    }

    @Override
    public void onDefend(Character defendingCharacter, Character targetCharacter) {
        playSound("sounds/attack.wav", 1000);
        onCharacterDamaged(targetCharacter, 1500);
    }

    @Override
    public void onAttack(Character attackingCharacter, Character targetCharacter) {
        playSound("sounds/attack.wav");
        onCharacterDamaged(targetCharacter, 500);
    }

    private void onCharacterDamaged(Character character, int delay) {
        boolean isDead = character.getCurrentHp() <= 0;
        String sound = isDead ? character.getDeathSound() : character.getDamagedSound();
        playSound(sound, delay);
    }

    public static void playErrorSound() {
        playSound("sounds/error.wav");
    }

    private static void playSound(String sound) {
        SoundPlayer sp = new SoundPlayer(sound, false);
        sp.start();
    }

    private static void playSound(String sound, int delay) {
        SoundPlayer sp = new SoundPlayer(sound, false);
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.start();
                Timer t = (Timer) e.getSource();
                t.stop();        
            }
        });
        timer.start();
    }
}
