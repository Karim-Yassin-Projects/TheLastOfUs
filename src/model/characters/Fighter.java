package model.characters;

import exceptions.*;
import model.collectibles.Supply;

public class Fighter extends Hero {

	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);

	}

	public void useSpecial() throws NoAvailableResourcesException, NotEnoughActionsException {
		if (this.getSupplyInventory().size() == 0) {
			throw new NoAvailableResourcesException();
		}
		this.setSpecialAction(true);
		Supply s = this.getSupplyInventory().get((int) Math.random() * this.getSupplyInventory().size());
		s.use(this);
		this.setActionsAvailable(getActionsAvailable() - 1);
	}

	@Override
	protected void checkCanAttack() throws NotEnoughActionsException {
		if (isSpecialAction())
			return;
		if (getActionsAvailable() == 0) {
			throw new NotEnoughActionsException();
		}
		setActionsAvailable(getActionsAvailable() - 1);
	}
}
