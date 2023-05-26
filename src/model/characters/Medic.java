package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;

public class Medic extends Hero {

	public Medic(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage, maxActions);
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		if (getTarget() instanceof Zombie)
			throw new InvalidTargetException("You can only heal fellow heroes.");
		if (getTarget() == null)
			throw new InvalidTargetException("Please select a friendly hero to heal.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to heal adjacent targets.");
		super.useSpecial();
		getTarget().setCurrentHp(getTarget().getMaxHp());
	}

	@Override
	public String getSpecialSound() {
		return "sounds/heal.wav";
	}

	@Override
	public String getSpecialActionText() {
		return "Heal";
	}
}
