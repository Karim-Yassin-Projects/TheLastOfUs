package model.characters;

import java.awt.Point;
import engine.*;
import exceptions.*;
import model.world.*;

public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	
	public void attack() throws InvalidTargetException, NotEnoughActionsException, Exception{
		
	}
	public void defend(Character c) {

	}
	public void onCharacterDeath() {
		Game.heroes.remove(this);
		Game.map[location.y][location.x] = new CharacterCell(null);
	}
	public boolean isAdjacent(Point p){
		int dx = p.x - location.x;
		int dy = p.y - location.y;
		return Math.abs(dx)<=1 && Math.abs(dy)<=1;
	}

}
