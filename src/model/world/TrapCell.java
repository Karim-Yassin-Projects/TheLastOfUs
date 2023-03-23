package model.world;
import java.util.Random;
public class TrapCell extends Cell{
	private int trapDamage;
	public TrapCell() {
		super();
		int[] arr = {10,20,30};
		int max = 2;
		int min = 0;
		Random r = new Random();
		int randomIndex = r.nextInt(max - min + 1) + min;
		this.trapDamage = arr[randomIndex];
	}
	public int getTrapDamage() {
		return trapDamage;
	}
}
