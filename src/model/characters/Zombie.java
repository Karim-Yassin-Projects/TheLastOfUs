package model.characters;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.Cell;
import model.world.CharacterCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;

	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}

	public void defend(Character c) {
		setTarget(c);
		c.setCurrentHp(c.getCurrentHp() - getAttackDmg() / 2);
	}

	public void onCharacterDeath() {
		Game.zombies.remove(this);
		Game.insertRandomZombie(new Zombie());
		super.onCharacterDeath();
	}

	@Override
	protected boolean hasValidAttackTarget() {
		return getTarget() instanceof Hero;
	}

	public void pickTarget() {
		setTarget(null);
		int zx = getLocation().x;
		int zy = getLocation().y;
		for (int x = Math.max(zx - 1, 0); x <= zx + 1 && x < 15; x++) {
			for (int y = Math.max(zy - 1, 0); y <= zy + 1 && y < 15; y++) {
				if (x == zx && y == zy) {
					continue;
				}
				Cell c = Game.map[x][y];
				if (!(c instanceof CharacterCell)) {
					continue;
				}
				CharacterCell charCell = (CharacterCell) c;
				if (!(charCell.getCharacter() instanceof Hero)) {
					continue;
				}
				setTarget(charCell.getCharacter());
				return;
			}
		}
	}

	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		pickTarget();
		super.attack();
	}

	@Override
	protected void checkCanAttack() throws NotEnoughActionsException {
	}

	public static void resetZombieCount() {
		ZOMBIES_COUNT = 1;
	}
}
