package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;

import database.Database;


import entities.Player;
public class FoffyMain extends Core implements KeyListener {

	public static void main(String[] args) {
		// wait for database to finish loading stuff
		Database.getInstance();
		new FoffyMain().run();
	}

	private Image bg;
	private Player player;
	private int posX;
	private double startTime;
	private int posY;

	// Init ved at kalde superklassens init
	public void init(){
		super.init();
		Point pos = new Point();
		startTime = System.currentTimeMillis();
		posX = 5;
		posY = 5;
		pos.setLocation(posX, posY);
		player = new Player("playersprite", Direction.SOUTH, pos);
		s.addKeyListener(this);
	}
	
	public synchronized void draw(Graphics2D g) {
		drawBackground(g);
		drawBottomTiles(g);
		drawPlayer(g);
		drawTopTiles(g);
		
		g.drawString("Player Coords: "+player.getX()+","+player.getY(), 200, 25+s.getInsets().top);
		g.drawString("Ghost Coords: "+player.getGhostX()+","+player.getGhostY(), 500, 25+s.getInsets().top);
		g.drawString("Map size (h*w): "+Database.getInstance().getLevelDimensions(0).height+","+Database.getInstance().getLevelDimensions(0).width, 300, 590+s.getInsets().top);
	}

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
	
	private void drawBackground(Graphics2D g) {
		bg = new ImageIcon("images"+File.separator+"gui"+File.separator+"background.png").getImage();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, s.getWidth(), s.getHeight());
		g.drawImage(bg, 0, s.getInsets().top, null);
	}
	
	private void drawPlayer(Graphics2D g) {
		Image picture = player.getAnimation().getImage();
		int x = 25+(player.getX()-player.getGhostX()+4)*50;
		int y = 50+(player.getY()-player.getGhostY()+4)*50;
		g.drawImage(picture, x, y+22, null);
	}

	/**
	 * Updates the screen
	 * @param timePassed Time since last update. 
	 */
	public void update(long timePassed) {
		player.update(timePassed);
	}

	/**
	 * Key events for controlling QMan on the map
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stop();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.movePlayer(Direction.EAST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.movePlayer(Direction.WEST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.movePlayer(Direction.SOUTH);
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.movePlayer(Direction.NORTH);
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
