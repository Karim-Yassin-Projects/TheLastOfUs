package model.collectibles;

import engine.Game;
import model.characters.Hero;

public class Supply implements Collectible {

	@Override
	public void pickUp(Hero h) {
		h.getSupplyInventory().add(this);
		h.updateSupplyCount(h.getSupplyInventory().size() - 1);
		Game.handleCollectiblePickUp(this);
	}

	@Override
	public void use(Hero h) {
		h.getSupplyInventory().remove(this);
		h.updateSupplyCount(h.getSupplyInventory().size() + 1);
	}

	@Override
	public String getImage() {
		return "images/supply.png";
	}

	@Override
	public String getSound() {
		return "sounds/supply.wav";
	}
}
