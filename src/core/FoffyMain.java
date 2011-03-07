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
import java.io.File;
import java.util.HashSet;

import javax.swing.ImageIcon;

import database.Database;

import entities.Entity;
import entities.LocalPlayer;
import entities.Player;
import enums.Direction;

public class FoffyMain extends Core implements KeyListener, MouseListener {
	public static void main(String[] args) {
		new FoffyMain().run();
	}

	private Image bg;
	private HashSet<Entity> entities;
	private HashSet<Entity> flyingEntities;
	private LocalPlayer player;

	/**
	 * Initialize everything by initializing super.init and specifying positions for the player.
	 */
	public void init(){
		super.init();
		entities = Database.getInstance().getStartEntities(1);
		Player lol = new Player("lol", "playersprite", new Point(5, 5));
		player = new LocalPlayer(lol);
		entities.add(lol);
		s.addKeyListener(this);
		s.addMouseListener(this);
		flyingEntities = new HashSet<Entity>();
	}

	/**
	 * Draw everything on the screen
	 */

	public synchronized void draw(Graphics2D g) {
		drawBackground(g);
		drawBottomTiles(g);
		drawNotFlyingEntities(g);
		drawFlyingEntities(g);
		drawTopTiles(g);

		Player p = player.getPlayer();
		g.drawString("Player Coords: "+p.getX()+","+p.getY(), 200, 25+s.getInsets().top);
		g.drawString("Ghost Coords: "+player.getGhostX()+","+player.getGhostY(), 500, 25+s.getInsets().top);
		g.drawString("Map size (h*w): "+Database.getInstance().getLevelDimensions(1).height+","+Database.getInstance().getLevelDimensions(1).width, 300, 590+s.getInsets().top);
	}

	/**
	 * Draws the - by the level - predefined tiles that are to be drawn below the player
	 */
	private void drawBottomTiles(Graphics2D g) {
		int x = 0, y = 0;
		for(int i = player.getGhostX()-4 ; i < player.getGhostX()+6; i++) {
			x = 0;
			for(int j = player.getGhostY()-4 ; j < player.getGhostY()+6 ; j++) {
				int tile1 = Database.getInstance().getTile(1, 0, i, j);
				int tile2 = Database.getInstance().getTile(1, 1, i, j);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile1), y*50+25, x*50+50+s.getInsets().top, null);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile2), y*50+25, x*50+50+s.getInsets().top, null);
				x += 1;
			}
			y += 1;
		}
	}

	/**
	 * Draws the - by the level - predefined tiles that are to be drawn above the player
	 */
	private void drawTopTiles(Graphics2D g) {
		int x = 0, y = 0;
		for(int i = player.getGhostX()-4 ; i < player.getGhostX()+6 ; i++) {
			x = 0;
			for(int j = player.getGhostY()-4 ; j < player.getGhostY()+6 ; j++) {
				int tile = Database.getInstance().getTile(1, 2, i, j);
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
	 * Draws the not flying Entities on the screen on position (x, y)
	 *
	 */
	private void drawNotFlyingEntities(Graphics2D g) {
		for(Entity e : entities) {
			if(e instanceof Drawable){
				if(e.getX() >= player.getGhostX()-4 && e.getX() <= player.getGhostX() + 5
						&& e.getY() >= player.getGhostY()-4 && e.getY() <= player.getGhostY() + 5) {
					if(!e.getFlying()) {
						drawEntity(g, e);
					}
					else{
						flyingEntities.add(e);
					}
				}	
			}
		}
	}

	/**
	 * Draws the wanted tile on the screen
	 * @param g The graphics engine
	 * @param e The Entity that is to be drawn
	 */
	private void drawEntity(Graphics2D g, Entity e){
			Drawable p = (Drawable)e;
			Image picture = p.getImage();
			int x = 25+(e.getX()-player.getGhostX()+4)*50;
			int y = 50+(e.getY()-player.getGhostY()+4)*50;
			g.drawImage(picture, x, y+s.getInsets().top, null);
	}

	/**
	 * Draw all flying Entities on the screen
	 * @param g The graphics engine
	 */
	private void drawFlyingEntities(Graphics2D g) {
		for(Entity e : flyingEntities) {
			drawEntity(g, e);
		}
		flyingEntities.clear();
	}

	/**
	 * Updates the screen
	 * @param timePassed Time since last update.
	 */
	public void update(long timePassed) {
		for(Entity e : entities) {
			try{
				Player pl = (Player)e;
				pl.update(timePassed);
			}catch(ClassCastException ex){}
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