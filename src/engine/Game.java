package engine;
import java.util.ArrayList;
import model.characters.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import model.characters.*;
import model.world.*;
public class Game {
	public static ArrayList<Hero> availableHeros;
	public static ArrayList<Hero> heros;
	public static ArrayList<Zombie> zombies;
	public static Cell [][] map;
	public static void loadHeroes(String filePath)throws IOException{
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
			Hero h = new Hero( n,mh,ma,ad); 
			availableHeros.add(h);
			
	
		}
	}
}
