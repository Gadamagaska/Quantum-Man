import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
public class FoffyMain extends Core implements KeyListener {

	public static void main(String[] args) {
		new FoffyMain().run();
	}

	private Image bg;
	private Player player;
	private int posX;
	private double startTime;
	private double currentTime;
	private int fpsCounter;
	private double fps;
	private int posY;
	private Animation animation;

	// Init ved at kalde superklassens init
	public void init(){
		super.init();
		Point pos = new Point();
		fpsCounter = 0;
		startTime = System.currentTimeMillis();
		posX = 5;
		posY = 5;
		pos.setLocation(posX, posY);
		animation = new Animation();
		for (int i = 0 ; i <= 2 ; i++) {
			animation.addScene(Database.getInstance().getTileImage("playersprite", i*4), 50);
		}
		player = new Player(animation, Direction.WEST, pos);
		Window w = s.getFullScreenWindow();
		w.addKeyListener(this);
		bg = new ImageIcon("images"+File.separator+"gui"+File.separator+"background.png").getImage();
	}

	private void drawTiles(Graphics2D g) {
		int x = 0, y = 0;
		for(int i = player.getGhostX()-4 ; i < player.getGhostX()+6; i++) {
			x = 0;
			for(int j = player.getGhostY()-4 ; j < player.getGhostY()+6 ; j++) {
				int tile1 = Database.getInstance().getTile(0, 0, i, j);
				int tile2 = Database.getInstance().getTile(0, 1, i, j);
				int tile3 = Database.getInstance().getTile(0, 2, i, j);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile1), y*50+25, x*50+50, null);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile2), y*50+25, x*50+50, null);
				g.drawImage(Database.getInstance().getTileImage("tileset1", tile3), y*50+25, x*50+50, null);
				x += 1;
			}
			y += 1;
		}
	}

	public void update(long timePassed) {
		animation.update(timePassed);
	}

	public synchronized void draw(Graphics2D g) {

		currentTime = (System.currentTimeMillis()-startTime) / 1000; // TODO: Fix this!
		fpsCounter += 1;
		if(currentTime == 3000) {
			fps = fpsCounter / (3*currentTime);
			fpsCounter = 0;
			startTime = currentTime;
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, s.getWidth(), s.getHeight());
		drawTiles(g);
		g.drawImage(bg, 0, 0, null);
		Image picture = player.getAnimation().getImage();
		g.drawImage(picture, player.getX()*50+25, player.getY()*50+50, null);
		g.drawString("Player Coords: "+player.getX()+","+player.getY(), 200, 25);
		g.drawString("Ghost Coords: "+player.getGhostX()+","+player.getGhostY(), 500, 25);
		g.drawString("Map size (h*w): "+Database.getInstance().getLevelDimensions(0).height+","+Database.getInstance().getLevelDimensions(0).width, 300, 590);
		g.drawString("FPS: "+fps, 50, 25);
	}
	// Keylistener interface
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stop();
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT && player.getPosition().x < 9) {
			player.movePlayer(Direction.EAST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT && player.getPosition().x > 0) {
			player.movePlayer(Direction.WEST);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN && player.getPosition().y < 9) {
			player.movePlayer(Direction.SOUTH);
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP && player.getPosition().y > 0) {
			player.movePlayer(Direction.NORTH);
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
