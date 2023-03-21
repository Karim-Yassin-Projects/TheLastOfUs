package model.characters;

import java.util.ArrayList;

import model.collectibles.*;

public class Hero extends Character{
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	public int getActionsAvailable() {
		return actionsAvailable;
	}
	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.actionsAvailable = maxActions;
	}
	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}
	public boolean isSpecialAction() {
		return specialAction;
	}
	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
	public int getMaxActions() {
		return maxActions;
	}
	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}
	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}
	
	
}
