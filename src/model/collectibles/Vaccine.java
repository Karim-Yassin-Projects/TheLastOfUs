package model.collectibles;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;

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

	}

}
