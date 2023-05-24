package model.world;

import java.awt.Point;

import engine.Game;

public abstract class Cell {

	private boolean isVisible;
	private Point location;

	public Cell() {

	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		if (this.isVisible == isVisible) {
			return;
		}
		this.isVisible = isVisible;
		Game.handleCellEvent(this);
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
