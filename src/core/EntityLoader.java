package core;

import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import database.Database;
import entities.Entity;
import entities.ImageEntity;
import entities.Player;

public class EntityLoader {
	HashSet<Entity> entities;
	Player player;
	
	public HashSet<Entity> getEntities() {
		return entities;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setEntities() {

		entities = new HashSet<Entity>();
		File file = new File("data","entities.txt");
		Database db = Database.getInstance();

		// Create an ArrayList with an ArrayList of the lines of the file
		ArrayList<ArrayList<String>> dataMap = new ArrayList<ArrayList<String>>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// Silent handling of exceptions
		}

		String line;

		// Split the line every time there is whitespace or equal symbol (=)
		try {
			while((line = reader.readLine()) != null) {
				dataMap.add(new ArrayList<String>(Arrays.asList(line.split("[\\s=]+"))));
			}
		} catch (IOException e) {
			// Silent handling of exceptions
		}

		for (ArrayList<String> i : dataMap) {	

			//Load Players
			if(i.get(0).equals("LOCAL_PLAYER") || i.get(0).equals("PLAYER")) {
				String name = i.get(2);
				String tileset = i.get(4);
				int posX = Integer.parseInt(i.get(6));
				int posY = Integer.parseInt(i.get(8));
				Player tempPlayer = new Player(name, tileset, new Point(posX, posY));

				// Add to entities HashSet
				entities.add(tempPlayer);
				
				// If the Player is LocalPlayer, make it so.
				if(i.get(0).equals("LOCAL_PLAYER")){
					player = tempPlayer;
				}
			}

			// Load ImageEntites
			if (i.get(0).equals("IMAGE_ENTITY")) {

				String tileset = i.get(4);
				int imageNumber = Integer.parseInt(i.get(6));
				int posX = Integer.parseInt(i.get(8));
				int posY= Integer.parseInt(i.get(10));

				Image image = db.getTileImage(tileset, imageNumber);
				ImageEntity entity = new ImageEntity(image, new Point(posX, posY));

				// Add to the entities HashSet
				entities.add(entity);

				// Set name of the Entity
				if(!i.get(2).equals("null")) {
					entity.setName(i.get(2));
				}

				// Set the flying boolean value of the Entity
				if(i.get(12).equals("true")) {
					entity.setFlying(true);
				}
			}			
		}
	}
}
