package core;

import interfaces.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.ImageIcon;

import database.Database;

import entities.Entity;
import entities.ImageEntity;
import entities.LocalPlayer;
import entities.Player;
import enums.Direction;
public class FoffyMain extends Core implements KeyListener, MouseListener {
	public static void main(String[] args) {
		Database db = Database.getInstance();
		new FoffyMain().run();
	}

	private Image bg;
	private HashSet<Entity> entities;
	private LocalPlayer player;

	/**
	 * Initialize everything by initializing super.init and specifying positions for the player.
	 */
	public void init(){
		super.init();
		entities = new HashSet<Entity>();

		createStuff();
		s.addKeyListener(this);
		s.addMouseListener(this);
	}

	private void createStuff() {
		Database db = Database.getInstance();
		Player first = new Player("You","playersprite",new Point(5,5));
		entities.add(first);
		setEntities();
		player = new LocalPlayer(first);
	}

	/**
	 * Draw everything on the screen
	 */
	public synchronized void draw(Graphics2D g) {
		drawBackground(g);
		drawBottomTiles(g);
		drawPlayer(g);
		drawTopTiles(g);

		Player p = player.getPlayer();
		g.drawString("Player Coords: "+p.getX()+","+p.getY(), 200, 25+s.getInsets().top);
		g.drawString("Ghost Coords: "+player.getGhostX()+","+player.getGhostY(), 500, 25+s.getInsets().top);
		g.drawString("Map size (h*w): "+Database.getInstance().getLevelDimensions(0).height+","+Database.getInstance().getLevelDimensions(0).width, 300, 590+s.getInsets().top);
	}

	/**
	 * Draws the tiles that are supposed to be drawn below the player
	 */
	private void drawBottomTiles(Graphics2D g) {
		int x = 0, y = 0;
		for(int i = player.getGhostX()-4 ; i < player.getGhostX()+6; i++) {
			x = 0;
			for(int j = player.getGhostY()-4 ; j < player.getGhostY()+6 ; j++) {
				int tile1 = Database.getInstance().getTile(0, 0, i, j);
				int tile2 = Database.getInstance().getTile(0, 1, i, j);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile1), y*50+25, x*50+50+s.getInsets().top, null);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile2), y*50+25, x*50+50+s.getInsets().top, null);
				x += 1;
			}
			y += 1;
		}
	}

	/**
	 * Draws the tiles that are supposed to be drawn on top of the player
	 */
	private void drawTopTiles(Graphics2D g) {
		int x = 0, y = 0;
		for(int i = player.getGhostX()-4 ; i < player.getGhostX()+6 ; i++) {
			x = 0;
			for(int j = player.getGhostY()-4 ; j < player.getGhostY()+6 ; j++) {
				int tile = Database.getInstance().getTile(0, 2, i, j);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile), y*50+25, x*50+50+s.getInsets().top, null);
				x += 1;
			}
			y += 1;
		}
	}

	/**
	 * Draws the background on the screen on position(x, y)
	 */
	private void drawBackground(Graphics2D g) {
		bg = new ImageIcon("images"+File.separator+"gui"+File.separator+"background.png").getImage();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, s.getWidth(), s.getHeight());
		g.drawImage(bg, 0, s.getInsets().top, null);
	}

	/**
	 * Draws the player on the screen on position (x, y)
	 *
	 */
	private void drawPlayer(Graphics2D g) {
		for(Entity e : entities) {
			if(e.getX() >= player.getGhostX()-4 && e.getX() <= player.getGhostX() + 5
					&& e.getY() >= player.getGhostY()-4 && e.getY() <= player.getGhostY() + 5) {
				try{
					Drawable p = (Drawable)e;
					Image picture = p.getImage();
					int x = 25+(e.getX()-player.getGhostX()+4)*50;
					int y = 50+(e.getY()-player.getGhostY()+4)*50;
					g.drawImage(picture, x, y+s.getInsets().top, null);
				} catch(Exception ex) {}				
			}
		}
		//Player p = player.getPlayer();
	}

	/**
	 * Updates the screen
	 * @param timePassed Time since last update.
	 */
	public void update(long timePassed) {
		player.getPlayer().update(timePassed);
		//TODO get player from entities HashSet.
	}

	public void setEntities() {

		File file = new File("data","imageEntities.txt");

		// Create an ArrayList with an ArrayList of the lines of the file
		ArrayList<ArrayList<String>> dataMap = new ArrayList<ArrayList<String>>();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// Silent handling of exceptions
		}

		String line;

		// Split the line every time there is whitespace or colon (:)
		try {
			while((line = reader.readLine()) != null) {
				dataMap.add(new ArrayList<String>(Arrays.asList(line.split("[\\s=]+"))));
			}
		} catch (IOException e) {
			// Silent handling of exceptions
		}

		/*
		 * Example output:
		 * 
		 * [
		 * 	[IMAGE_ENTITY, NAME, "", TILESET, "tileset1", IMAGE, 35, POSX, 1, POSY, 7]
		 * ]
		 * 
		 */
		for (ArrayList<String> i : dataMap) {		
			if (i.get(0).equals("IMAGE_ENTITY")) {
				Database db = Database.getInstance();
				String name = null;
				if(!i.get(2).equals("null")) {
					name = i.get(2);
				}
				String tileset = i.get(4);
				int imageNumber = Integer.parseInt(i.get(6));
				int posX = Integer.parseInt(i.get(8));
				int posY= Integer.parseInt(i.get(10));
				Image image = db.getTileImage(tileset, imageNumber);
				
				System.out.println(i);
				System.out.println(name+","+tileset+","+imageNumber+","+posX+","+posY);
				ImageEntity entity = new ImageEntity(image, new Point(posX, posY));
				entities.add(entity);
				if(name != "null") {
					entity.setName(name);
				}
			}

		}
	}

	/**
	 * Key events for controlling QMan on the map
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stop();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move(Direction.EAST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move(Direction.WEST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.move(Direction.SOUTH);
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.move(Direction.NORTH);
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {
		int mouseX = ((e.getX()-25)/50+player.getGhostX()-4);
		int mouseY = ((e.getY()-50-s.getInsets().top)/50+player.getGhostY()-4);
		for(Entity entity : entities) {
			if(mouseX == entity.getX() && mouseY == entity.getY()) {
				System.out.println(entity.getName()+" ("+mouseX+","+mouseY+")");
				break;
			}
		}

	}

	public void mouseEntered(MouseEvent e) {		
	}

	public void mouseExited(MouseEvent e) {		
	}

	public void mousePressed(MouseEvent e) {		
	}

	public void mouseReleased(MouseEvent e) {		
	}
}