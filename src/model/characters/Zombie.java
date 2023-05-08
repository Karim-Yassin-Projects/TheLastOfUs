package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void attack() throws Exception, InvalidTargetException, NotEnoughActionsException {
		if (this.getTarget() == null) {
			throw new InvalidTargetException();
		}
		if (getTarget() instanceof Zombie) {
			throw new InvalidTargetException();
		}
		if (isAdjacent(getTarget().getLocation())) {
			getTarget().setCurrentHp(getTarget().getCurrentHp() - this.getAttackDmg());
			getTarget().defend(this);
		} else
			throw new InvalidTargetException();
		if (getCurrentHp() == 0) {
			onCharacterDeath();
		}
		if (getTarget().getCurrentHp() == 0) {
			getTarget().onCharacterDeath();
		}
	}

	public void defend(Character c) {
		setTarget(c);
		c.setCurrentHp(c.getCurrentHp() - getAttackDmg() / 2);
	}

	public void onCharacterDeath() {
		Game.zombies.remove(this);
		Game.map[getLocation().y][getLocation().x] = new CharacterCell(null);
		Game.insertRandomZombie(new Zombie());

	}
}
