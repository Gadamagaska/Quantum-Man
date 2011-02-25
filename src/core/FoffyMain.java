package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashSet;

import javax.swing.ImageIcon;

import database.Database;


import entities.ImageEntity;
import entities.LocalPlayer;
import entities.Player;
import enums.Direction;
public class FoffyMain extends Core implements KeyListener {
	public static void main(String[] args) {
		// wait for database to finish loading stuff
		Database.getInstance();
		new FoffyMain().run();
	}

	private Image bg;
	private HashSet<Player> characters;
	private HashSet<ImageEntity> entities;
	private LocalPlayer player;

	/**
	 * Initialize everything by initializing super.init and specifying positions for the player.
	 */
	public void init(){
		super.init();
		characters = new HashSet<Player>();
		// TODO: Skal indeholde "Character", men den siger
		// player ikke kan vaere deri? men den nedarver da derfra

		createStuff();
		s.addKeyListener(this);
	}

	private void createStuff() {
		Player first = new Player("Nezbo","playersprite",new Point(5,5));
		characters.add(first);
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
	 */ //TODO: even more testing!
	private void drawPlayer(Graphics2D g) {
		Player p = player.getPlayer();
		Image picture = p.getImage();
		int x = 25+(p.getX()-player.getGhostX()+4)*50;
		int y = 50+(p.getY()-player.getGhostY()+4)*50;
		g.drawImage(picture, x, y+22, null);
	}

	/**
	 * Updates the screen
	 * @param timePassed Time since last update.
	 */
	public void update(long timePassed) {
		for(Player p : characters){
			p.update(timePassed);
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
}