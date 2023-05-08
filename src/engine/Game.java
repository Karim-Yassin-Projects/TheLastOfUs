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

	public static Cell[][] map = new Cell[15][15];
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

	private static Point getRandomEmptyCell() {
		do {
			int x = (int) (Math.random() * 15);
			int y = (int) (Math.random() * 15);
			if (Game.map[x][y] == null) {
				Game.map[x][y] = new CharacterCell(null);
				return new Point(x, y);
			}

			if (!(Game.map[x][y] instanceof CharacterCell)) {
				continue;
			}
			if (((CharacterCell)Game.map[x][y]).getCharacter() == null){
				return new Point(x, y);
			}
		}while(true);
	}

	public static void insertRandomZombie(Zombie z) {
		Point p = getRandomEmptyCell();
		CharacterCell c = (CharacterCell)Game.map[p.x][p.y];
		c.setCharacter(z);
		z.setLocation(p);
		zombies.add(z);
		
	}

	public static void insertRandomCollectible(Collectible c) {
		Point p = getRandomEmptyCell();
		CollectibleCell cell = new CollectibleCell(c);
		Game.map[p.x][p.y] = cell;
	}

	public static void insertTraps() {
		Point p = getRandomEmptyCell();
		TrapCell cell = new TrapCell();
		Game.map[p.x][p.y] = cell;
	}

	public static void startGame(Hero h) {
		Game.availableHeroes.remove(h);
		Game.heroes.add(h);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				Game.map[i][j] = new CharacterCell(null);
			}
		}
		h.setLocation(new Point(0, 0));
		Game.map[0][0] = new CharacterCell(h);
		h.handleMovementVisibility();
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
