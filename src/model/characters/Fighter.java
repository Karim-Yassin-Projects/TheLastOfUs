package model.characters;

import exceptions.*;
import model.collectibles.Supply;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	public void attack() throws InvalidTargetException, NotEnoughActionsException{
		if(this.getTarget() == null) {
			throw new InvalidTargetException();
		}
		if(getTarget() instanceof Hero) {
			throw new InvalidTargetException();
		}
		if(this.getActionsAvailable() != 0) {
			if(this.isSpecialAction() == false)
			this.setActionsAvailable(getActionsAvailable()-1);
			if(isAdjacent(getTarget().getLocation())) {
				getTarget().setCurrentHp(getTarget().getCurrentHp()-this.getAttackDmg());
				getTarget().defend(this);
			}
			else
				throw new InvalidTargetException();
		}
		else {
			throw new NotEnoughActionsException();
		}
			
		if(getCurrentHp() == 0) {
			onCharacterDeath();
		}
		if(getTarget().getCurrentHp() == 0) {
			getTarget().onCharacterDeath();
		}
	}
	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException {
		if(this.getSupplyInventory().size() == 0) {
			throw new NoAvailableResourcesException();
		}
		this.setSpecialAction(true);
		Supply s = this.getSupplyInventory().get((int)Math.random()*this.getSupplyInventory().size());
		s.use(this);
		this.setActionsAvailable(getActionsAvailable()-1);
	}

}
