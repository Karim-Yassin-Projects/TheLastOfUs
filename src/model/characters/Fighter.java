package model.characters;

public class Fighter extends Hero {

	public Fighter(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage, maxActions);
	}

	@Override
	public String getSpecialSound() {
		return "sounds/war_cry.wav";
	}

	public String getSpecialActionText() {
		return "Unlimited Attacks";
	}
}
