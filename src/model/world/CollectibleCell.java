package model.world;

import model.collectibles.*;

public class CollectibleCell extends Cell {
	public CollectibleCell(Collectible collectible) {
		super();
		this.collectible = collectible;
	}

	private Collectible collectible;

	public Collectible getCollectible() {
		return collectible;
	}
}
