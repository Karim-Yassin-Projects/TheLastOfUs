package engine;

import model.characters.Hero;
import model.collectibles.Collectible;
import model.characters.Character;
import model.world.Cell;

public abstract class GameListener {
    public void onCellChanged(int i, int j, Cell oldCell, Cell newCell) {

    }

    public void onSelectedHeroChange(Hero oldHero, Hero newHero) {

    }

    public void onTargetChanged(Character oldTarget, Character newTarget) {

    }

    public void onCellEventChanged(int i, int j, Cell cell) {

    }

    public void onHeroAdded(Hero hero) {

    }

    public void onHeroRemoved(Hero hero) {
    
    }

    public void onHeroSpecial(Hero hero) {
    
    }

    public void onTrapCell(Cell cell) {
    
    }

    public void onDefend(Character defendingCharacter, Character targetCharacter) {

    }

    public void onAttack(Character attackingCharacter, Character targetCharacter) {

    }

    public boolean onGameOver() {
        return false;
    }

    public void onCollectiblePickUp(Collectible collectible) {

    }
}
