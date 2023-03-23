package model.world;

import java.util.Random;

public class TrapCell extends Cell {
	private int trapDamage;

	public TrapCell() {
		super();
		int[] arr = { 10, 20, 30 };
		Random r = new Random();
		int randomIndex = r.nextInt(arr.length);
		this.trapDamage = arr[randomIndex];
	}

	public int getTrapDamage() {
		return trapDamage;
	}
}
