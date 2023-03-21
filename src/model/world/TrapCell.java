package model.world;
public class TrapCell {
	private int trapDamage;
	public TrapCell() {
		super();
		int[] arr = {10,20,30};
		this.trapDamage = arr[(int)Math.random()*2 + 1];
	}
	public int getTrapDamage() {
		return trapDamage;
	}
}
