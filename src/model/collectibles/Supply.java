package model.collectibles;

import model.characters.*;
import exceptions.*;

public class Supply implements Collectible {
	public Supply() {

	}

	public void pickUp(Hero h) {
		h.getSupplyInventory().add(this);
	}

	public void use(Hero h) throws NoAvailableResourcesException {
		if (h.getSupplyInventory().isEmpty())
			throw new NoAvailableResourcesException();
		else {
			h.getSupplyInventory().remove(this);
		}
	}

}
