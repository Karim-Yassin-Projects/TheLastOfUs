package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import model.characters.Character;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Game {
	private static Hero selectedHero;
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell[][] map = new Cell[15][15];
	private static ArrayList<GameListener> gameListeners = new ArrayList<>();

	public static void loadHeroes(String filePath) throws IOException {
		availableHeroes = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] sp = line.split(",");
			Hero h;
			if (sp[1].equals("EXP")) {
				h = new Explorer(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			} else if (sp[1].equals("FIGH")) {
				h = new Fighter(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			} else {
				h = new Medic(sp[0], Integer.parseInt(sp[2]), Integer.parseInt(sp[4]), Integer.parseInt(sp[3]));
			}
			availableHeroes.add(h);
			line = br.readLine();
		}
		br.close();
	}

	public static void endTurn() throws NotEnoughActionsException, InvalidTargetException {
		for (int i = 0; i < zombies.size(); i++) {
			Zombie zombie = zombies.get(i);
			zombie.attack();
			if (handleGameOver()) {
				return;
			}
			zombie.setTarget(null);
			if (zombie.getCurrentHp() <= 0) {
				i--;
			}
		}
		spawnNewZombie();
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++)
				map[i][j].setVisible(false);
		for (Hero hero : heroes) {
			hero.setActionsAvailable(hero.getMaxActions());
			hero.setTarget(null);
			hero.setSpecialAction(false);
			adjustVisibility(hero);
		}
	}

	public static void adjustVisibility(Hero h) {
		Point p = h.getLocation();
		if (p == null) {
			return;
		}
		for (int i = -1; i <= 1; i++) {
			int cx = p.x + i;
			if (cx >= 0 && cx <= 14) {
				for (int j = -1; j <= 1; j++) {
					int cy = p.y + j;
					if (cy >= 0 && cy <= 14) {
						if (cy >= 0 && cy <= map.length - 1) {
							if (map[cx][cy] != null) {
								map[cx][cy].setVisible(true);
							}
						}
					}
				}
			}
		}
	}

	public static void spawnNewZombie() {
		Zombie z = new Zombie();
		zombies.add(z);
		int x, y;
		do {
			x = ((int) (Math.random() * map.length));
			y = ((int) (Math.random() * map[x].length));
		} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
				|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
		z.setLocation(new Point(x, y));
		setCell(x, y, new CharacterCell(z));
	}

	public static boolean checkWin() {
		int remainingVaccines = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] instanceof CollectibleCell
						&& ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
					remainingVaccines++;
			}
		}
		for (Hero hero : heroes) {
			remainingVaccines += hero.getVaccineInventory().size();
		}
		return heroes.size() >= 5 && remainingVaccines == 0;
	}

	public static boolean checkGameOver() {
		if (heroes.size() > 0) {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					if (map[i][j] instanceof CollectibleCell
							&& ((CollectibleCell) map[i][j]).getCollectible() instanceof Vaccine)
						return false;
				}
			}
			for (Hero hero : heroes) {
				if (hero.getVaccineInventory().size() > 0)
					return false;
			}
		}
		return true;
	}

	public static void startGame(Hero h) {
		addHero(h);
		availableHeroes.remove(h);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				setCell(i, j, new CharacterCell(null));
			}
		}

		((CharacterCell) map[0][0]).setCharacter(h);
		h.setLocation(new Point(0, 0));

		spawnCollectibles();
		for (int i = 0; i < 10; i++) {
			spawnNewZombie();
		}
		spawnTraps();
		adjustVisibility(h);
	}

	public static void spawnCollectibles() {
		for (int i = 0; i < 5; i++) {
			Vaccine v = new Vaccine();
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			setCell(x, y, new CollectibleCell(v));
		}
		for (int i = 0; i < 5; i++) {
			Supply v = new Supply();
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			setCell(x, y, new CollectibleCell(v));
		}
	}

	public static void spawnTraps() {
		for (int i = 0; i < 5; i++) {
			int x, y;
			do {
				x = ((int) (Math.random() * map.length));
				y = ((int) (Math.random() * map[x].length));
			} while ((map[x][y] instanceof CharacterCell && ((CharacterCell) map[x][y]).getCharacter() != null)
					|| (map[x][y] instanceof CollectibleCell) || (map[x][y] instanceof TrapCell));
			setCell(x, y, new TrapCell());
		}
	}

	public static Hero getSelectedHero() {
		return selectedHero;
	}

	public static void addGameListener(GameListener listener) {
		gameListeners.add(listener);
	}

	public static void removeGameListener(GameListener listener) {
		gameListeners.remove(listener);
	}

	public static void setSelectedHero(Hero selectedHero) {
		if (selectedHero == Game.selectedHero) {
			return;
		}

		Hero oldHero = Game.selectedHero;
		Game.selectedHero = selectedHero;
		for (GameListener gameListener : gameListeners) {
			gameListener.onSelectedHeroChange(oldHero, Game.selectedHero);
		}
		Character oldTarget = oldHero == null ? null : oldHero.getTarget();
		Character newTarget = selectedHero == null ? null : selectedHero.getTarget();
		onTargetChanged(oldTarget, newTarget);
	}

	public static void setCell(int i, int j, Cell newCell) {
		Cell oldCell = map[i][j];
		if (oldCell == newCell) {
			return;
		}
		map[i][j] = newCell;

		Point p = new Point(i, j);
		newCell.setLocation(p);
		if (oldCell != null && oldCell.isVisible()) {
			newCell.setVisible(true);
		}
		for (GameListener gameListener : gameListeners) {
			gameListener.onCellChanged(i, j, oldCell, newCell);
		}
	}

	public static void onTargetChanged(Character oldCharacter, Character newCharacter) {
		if (oldCharacter == newCharacter) {
			return;
		}
		for (GameListener listener : gameListeners) {
			listener.onTargetChanged(oldCharacter, newCharacter);
		}
	}

	public static void handleCellEvent(Cell cell) {
		if (cell.getLocation() == null) {
			return;
		}
		Point p = cell.getLocation();
		for (GameListener listener : gameListeners) {
			listener.onCellEventChanged(p.x, p.y, cell);
		}
	}

	public static void addHero(Hero h) {
		if (heroes.contains(h)) {
			return;
		}
		heroes.add(h);
		for (GameListener listener : gameListeners) {
			listener.onHeroAdded(h);
		}
	}

	public static void removeHero(Hero h) {
		if (!heroes.contains(h)) {
			return;
		}
		heroes.remove(h);
		for (GameListener listener : gameListeners) {
			listener.onHeroRemoved(h);
		}
	}

	public static void handleTrapCells(Cell cell) {
		if (!(cell instanceof TrapCell)) {
			return;
		}
		for (GameListener listener : gameListeners) {
			listener.onTrapCell(cell);
		}
	}

	public static boolean handleGameOver() {
		if (!checkGameOver()) {
			return false;
		}
		for (GameListener listener : gameListeners) {
			if (listener.onGameOver()) {
				return true;
			}
		}
		return false;
	}

	public static void resetGame() {
		// while (!heroes.isEmpty()) {
		// Hero h = heroes.get(0);
		// heroes.remove(h);
		// availableHeroes.add(h);
		// }
		heroes = new ArrayList<>();
		gameListeners = new ArrayList<>();
		zombies = new ArrayList<>();
		selectedHero = null;
		Game.map = new Cell[15][15];
	}

    public static void handleHeroSpecial(Hero hero) {
		for (GameListener listener : gameListeners) {
			listener.onHeroSpecial(hero);
		}
    }

    public static void handleDefend(Character defendingCharacter, Character targetCharacter) {
		for (GameListener listener : gameListeners) {
			listener.onDefend(defendingCharacter, targetCharacter);
		}
	}

	public static void handleAttack(Character attackingCharacter, Character targetCharacter) {
		for (GameListener listener : gameListeners) {
			listener.onAttack(attackingCharacter, targetCharacter);
		}
	}

    public static void handleCollectiblePickUp(Collectible collectible) {
		for (GameListener listener : gameListeners) {
			listener.onCollectiblePickUp(collectible);
		}
    }
}
