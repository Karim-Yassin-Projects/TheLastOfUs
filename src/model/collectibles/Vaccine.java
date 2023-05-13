package model.collectibles;

import java.awt.Point;

import engine.Game;
import exceptions.NoAvailableResourcesException;
import model.characters.Hero;
import model.world.CharacterCell;

public class Vaccine implements Collectible {

	public Vaccine() {

	}

	public void pickUp(Hero h) {
		h.getVaccineInventory().add(this);
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		if (h.getVaccineInventory().isEmpty())
			throw new NoAvailableResourcesException();
		else {
			h.getVaccineInventory().remove(this);
		}

		Point p = h.getTarget().getLocation();
		Game.zombies.remove(h.getTarget());
		int index = (int) (Game.random.nextInt(Game.availableHeroes.size()));
		Hero newHero = Game.availableHeroes.get(index);
		newHero.setLocation(h.getTarget().getLocation());
		Game.availableHeroes.remove(index);
		Game.map[p.x][p.y] = new CharacterCell(newHero);
		Game.heroes.add(newHero);
		h.setTarget(null);
	}

}
