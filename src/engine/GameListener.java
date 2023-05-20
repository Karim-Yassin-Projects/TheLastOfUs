package engine;

import model.characters.Hero;
import model.world.Cell;

public abstract class GameListener {
    
    public void onCellChanged(int x, int y, Cell oldCell, Cell newCell) {
    }
    public void onSelectedHeroChanged(Hero oldSelection, Hero newSelection){
    }
}
