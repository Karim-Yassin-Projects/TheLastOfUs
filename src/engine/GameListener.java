package engine;

import model.characters.Hero;
import model.characters.Character;
import model.world.Cell;

public abstract class GameListener {
    public void onCellChanged(int i, int j, Cell oldCell, Cell newCell){
        
    }
    public void onSelectedHeroChange(Hero oldHero, Hero newHero){
        
    }
    public void onTargetChanged(Character oldTarget, Character newTarget) {

    }
    public void onCellVisibilityChanged(int i, int j, Cell cell){
        
    }
}
