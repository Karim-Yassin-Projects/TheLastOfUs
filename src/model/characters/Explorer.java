package model.characters;

import engine.Game;
import exceptions.*;
import model.collectibles.Supply;

public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException {
		if(this.getActionsAvailable() == 0) {
			throw new NotEnoughActionsException();
		}
		else {
		if(this.getSupplyInventory().isEmpty()) {
			throw new NoAvailableResourcesException();
		}
		else {
		this.setSpecialAction(true);
		Supply s = this.getSupplyInventory().get(Game.random.nextInt(this.getSupplyInventory().size()));
		s.use(this);
		for(int i = 0; i<15; i++) {
			for(int j = 0; j<15; j++) {
				Game.map[i][j].setVisible(true);
			}
		}
		
		}
		this.setActionsAvailable(getActionsAvailable()-1);
	}
	}

	
}
