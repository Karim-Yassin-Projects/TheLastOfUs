package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.*;
import exceptions.*;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	private boolean specialAction;

	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.vaccineInventory = new ArrayList<Vaccine>();
		this.supplyInventory = new ArrayList<Supply>();
		this.specialAction = false;

	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void handleMovementVisibility() {
		Point loc = this.getLocation();
		if (loc == null) {
			loc = findHero();
			if (loc == null) {
				return;
			}
			setLocation(loc);
		}
		for (int i = Math.max(0, loc.x - 1); i <= loc.x + 1 && i < 15; i++) {
			for (int j = Math.max(0, loc.y - 1); j <= loc.y + 1 && j < 15; j++) {
				if (Game.map[i][j] == null) {
					Game.map[i][j] = new CharacterCell(i == loc.x && j == loc.y ? this : null);
				}
				Game.map[i][j].setVisible(true);
			}

		}
	}

	private Point findHero() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Cell c = Game.map[i][j];
				if (c instanceof CharacterCell
						&& ((CharacterCell) c).getCharacter() == this) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}

	public Cell handleCharacterCell(Cell c) throws MovementException {
		CharacterCell cell = (CharacterCell) c;
		if (cell.getCharacter() != null)
			throw new MovementException();
		else {
			cell.setCharacter(this);
			actionsAvailable--;
		}
		return c;
	}

	public Cell handleTrapCell(Cell c) {
		TrapCell cell = (TrapCell) c;
		this.setCurrentHp(getCurrentHp() - cell.getTrapDamage());
		c = new CharacterCell(this);
		actionsAvailable--;
		return c;

	}

	public Cell handleCollectibleCell(Cell c) {
		CollectibleCell cell = (CollectibleCell) c;
		cell.getCollectible().pickUp(this);
		c = new CharacterCell(this);
		actionsAvailable--;
		return c;

	}

	public void onCharacterDeath() {
		Game.heroes.remove(this);
		super.onCharacterDeath();
	}

	public void defend(Character c) {
		c.setCurrentHp(c.getCurrentHp() - getAttackDmg() / 2);
	}

	public void useSpecial()
			throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException, Exception {
	}

	public void cure() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException {
		if (this.actionsAvailable == 0) {
			throw new NotEnoughActionsException();
		} else {
			if (!(getTarget() instanceof Zombie)) {
				throw new InvalidTargetException();
			}
			if (!isAdjacent(getTarget().getLocation())) {
				throw new InvalidTargetException();
			}
			Vaccine v = this.vaccineInventory.get(Game.random.nextInt(this.vaccineInventory.size()));
			v.use(this);
			this.actionsAvailable--;
		}
	}

	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if (actionsAvailable == 0) {
			throw new NotEnoughActionsException();
		}
		Point newLocation = new Point(this.getLocation().x, this.getLocation().y);
		if (d == Direction.DOWN) {
			newLocation.x--;
		} else if (d == Direction.UP) {
			newLocation.x++;
		} else if (d == Direction.RIGHT) {
			newLocation.y++;
		} else {
			newLocation.y--;
		}
		if (newLocation.x < 0 || newLocation.y < 0 || newLocation.x > 14 || newLocation.y > 14) {
			throw new MovementException();
		}
		Cell oldCell = Game.map[newLocation.x][newLocation.y];
		Cell newCell = null;
		if (oldCell instanceof CharacterCell) {
			newCell = handleCharacterCell(oldCell);
		} else if (oldCell instanceof TrapCell) {
			newCell = handleTrapCell(oldCell);
		} else if (oldCell instanceof CollectibleCell) {
			newCell = handleCollectibleCell(oldCell);
		}
		((CharacterCell)Game.map[getLocation().x][getLocation().y]).setCharacter(null);
		setLocation(newLocation);
		Game.map[getLocation().x][getLocation().y] = newCell;
		if (getCurrentHp() == 0) {
			onCharacterDeath();
		} else {
			handleMovementVisibility();
		}
	}

	@Override
	protected boolean hasValidAttackTarget() {
		return getTarget() instanceof Zombie;
	}

	@Override
	protected void checkCanAttack() throws NotEnoughActionsException {
		if (actionsAvailable == 0) {
			throw new NotEnoughActionsException();
		}
		actionsAvailable--;
	}

	@Override
	public void attack() throws InvalidTargetException, NotEnoughActionsException {
		super.attack();
	}
}
