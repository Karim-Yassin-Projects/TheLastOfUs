package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.world.*;
import model.collectibles.*;

public class Game {

	public static Cell[][] map;
	public static ArrayList<Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();

	public static void loadHeroes(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero = null;
			switch (content[1]) {
				case "FIGH":
					hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
				case "MED":
					hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
				case "EXP":
					hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
							Integer.parseInt(content[3]));
					break;
			}
			availableHeroes.add(hero);
			line = br.readLine();

		}
		br.close();
	}

	public static void insertRandomZombie(Zombie z) {
		int x;
		int y;
		boolean flag = false;
		do {
			x = (int) Math.random() * 15;
			y = (int) Math.random() * 15;
			if (Game.map[x][y] == null) {
				Game.map[x][y] = new CharacterCell(null);
			}

			if (!(Game.map[x][y] instanceof CharacterCell)) {
				continue;
			}
			CharacterCell c = (CharacterCell) Game.map[x][y];
			if (c.getCharacter() != null) {
				continue;
			}
			c.setCharacter(z);
			z.setLocation(new Point(x, y));
			zombies.add(z);
			flag = true;
		} while (flag == false);

	}

	public static void insertRandomCollectible(Collectible c) {
		int x;
		int y;
		boolean flag = false;
		do {
			x = (int) Math.random() * 15;
			y = (int) Math.random() * 15;
			if (x != 0 && y != 0) {
				if (!(Game.map[x][y] instanceof CharacterCell) && !(Game.map[x][y] instanceof TrapCell)
						&& !(Game.map[x][y] instanceof CollectibleCell)) {
					CollectibleCell cell = (CollectibleCell) Game.map[x][y];
					cell = new CollectibleCell(c);
					flag = true;
				}

			}
		} while (flag == false);

	}

	public static void insertTraps() {
		int x;
		int y;
		boolean flag = false;
		do {
			x = (int) Math.random() * 15;
			y = (int) Math.random() * 15;
			if (x != 0 && y != 0) {
				if (!(Game.map[x][y] instanceof CharacterCell) && !(Game.map[x][y] instanceof CollectibleCell)
						&& !(Game.map[x][y] instanceof TrapCell)) {
					TrapCell cell = (TrapCell) Game.map[x][y];
					cell = new TrapCell();
					flag = true;
				}

			}
		} while (flag == false);

	}

	public static void startGame(Hero h) {
		Game.availableHeroes.remove(h);
		Game.heroes.add(h);
		Game.map = new Cell[15][15];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Game.map[i][j] = new CharacterCell(null);
			}
		}
		h.setLocation(new Point(0, 0));
		Game.map[0][0] = new CharacterCell(h);
		int i = 10;
		int j = 5;
		while (i > 0) {
			insertRandomZombie(new Zombie());
			i--;
		}
		while (j > 0) {
			insertRandomCollectible(new Supply());
			insertRandomCollectible(new Vaccine());
			insertTraps();
			j--;
		}

	}

	public static boolean checkWin() {

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (Game.map[i][j] instanceof CollectibleCell) {
					CollectibleCell c = (CollectibleCell) Game.map[i][j];
					if (c.getCollectible() instanceof Vaccine)
						return false;
				}
			}
		}
		if (Game.heroes.size() >= 5) {
			for (int i = 0; i < Game.heroes.size(); i++) {
				if (Game.heroes.get(i).getVaccineInventory().size() != 0) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean checkGameOver() {
		boolean flag1 = true;
		boolean flag2 = true;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (Game.map[i][j] instanceof CollectibleCell) {
					CollectibleCell c = (CollectibleCell) Game.map[i][j];
					if (c.getCollectible() instanceof Vaccine)
						flag1 = false;
				}
			}
		}
		boolean flag3 = Game.heroes.size() == 0;
		for (int i = 0; i < Game.heroes.size(); i++) {
			if (Game.heroes.get(i).getVaccineInventory().size() != 0) {
				flag2 = false;
			}
		}
		return (flag1 && flag2) || flag3;
	}

	public static void endTurn() throws InvalidTargetException, NotEnoughActionsException, Exception {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (Game.map[i][j] instanceof CharacterCell) {
					CharacterCell cell = (CharacterCell) Game.map[i][j];
					if (cell.getCharacter() instanceof Hero) {
						Hero h = (Hero) cell.getCharacter();
						h.setTarget(null);
						h.setActionsAvailable(h.getMaxActions());
						h.setSpecialAction(false);
						for (int a = 0; a < 15; a++) {
							for (int b = 0; b < 15; b++) {
								Point p = new Point(a, b);
								if (h.isAdjacent(p)) {
									Game.map[a][b].setVisible(true);
								} else {
									Game.map[a][b].setVisible(false);
								}
							}
						}
					} else {
						boolean flag = false;
						for (int k = 0; k < 15; k++) {
							for (int z = 0; z < 15; z++) {
								if (Game.map[k][z] instanceof CharacterCell) {
									CharacterCell c = (CharacterCell) Game.map[k][z];
									if (c.getCharacter() instanceof Hero) {
										if (c.getCharacter().isAdjacent(cell.getCharacter().getLocation())) {
											cell.getCharacter().setTarget(c.getCharacter());
											cell.getCharacter().attack();
											cell.getCharacter().setTarget(null);
											flag = true;
											if (cell.getCharacter().getCurrentHp() == 0) {
												cell.getCharacter().onCharacterDeath();
											}
										}
									}
								}
							}
							if (flag) {
								break;
							}
						}
					}

				}
			}
		}
		if (Game.zombies.size() < 10)
			insertRandomZombie(new Zombie());

	}

}
