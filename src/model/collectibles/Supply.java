package model.collectibles;

import model.characters.Hero;


public class Supply implements Collectible {

	@Override
	public void pickUp(Hero h) {
		h.getSupplyInventory().add(this);
		h.raiseSupplyCountChange(h.getSupplyInventory().size()-1);
	}

	@Override
	public void use(Hero h) {
		h.getSupplyInventory().remove(this);
		h.raiseSupplyCountChange(h.getSupplyInventory().size()+1);
	}
	@Override
	public String getImage() {
		return "images/supply.png";
	}
}
