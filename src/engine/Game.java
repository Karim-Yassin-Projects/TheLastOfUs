package engine;
import java.util.ArrayList;
import model.characters.*;
import model.collectibles.Collectible;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import model.characters.*;
import model.world.*;
public class Game {
	public static ArrayList<Hero> availableHeroes;
	public static ArrayList<Hero> heroes;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;
	public static void loadHeroes(String filePath)throws Exception{
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while((currentLine = br.readLine()) != null) {
			
			String [] s = currentLine.split(",");
			String n = s[0];
			String t = s[1];
			int mh = Integer.parseInt(s[2]);
			int ma = Integer.parseInt(s[3]);
			int ad = Integer.parseInt(s[4]);
			switch(t) {
			case "FIGH": availableHeroes.add(new Fighter(n,mh,ad,ma));break;
			case "EXP": availableHeroes.add(new Explorer(n,mh,ad,ma));break;
			case "MED": availableHeroes.add(new Medic(n,mh,ad,ma));break;
			default: throw new Exception();
			}
	
		}
	}
}
