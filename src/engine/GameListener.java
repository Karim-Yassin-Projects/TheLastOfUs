package engine;

import model.world.Cell;

public abstract class GameListener {
    
    public void onCellChanged(int x, int y, Cell oldCell, Cell newCell) {
    }
}
