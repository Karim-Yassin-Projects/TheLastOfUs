package model.characters;

import model.collectibles.Supply;
import exceptions.*;
public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
	}
	
	public void useSpecial() throws Exception, InvalidTargetException{
		if(getTarget() instanceof Hero) {
			this.setSpecialAction(true);
			getTarget().setCurrentHp(getTarget().getMaxHp());
		}
		else {
			throw new InvalidTargetException();
		}
		if(this.getSupplyInventory().isEmpty()) {
			throw new NoAvailableResourcesException();
		}
		else {
		Supply s = this.getSupplyInventory().get((int)Math.random()*this.getSupplyInventory().size());
		s.use(this);
		if(!isAdjacent(getTarget().getLocation())){
			throw new InvalidTargetException();
		}
		}
	}

}
