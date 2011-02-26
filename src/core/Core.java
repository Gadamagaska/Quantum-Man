package core;


import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;

import database.Database;


public abstract class Core {
	
	private static DisplayMode[] modes = {
		new DisplayMode(800, 600, 32, 0),
		new DisplayMode(800, 600, 24, 0),
		new DisplayMode(800, 600, 16, 0),
		new DisplayMode(640, 480, 32, 0),
		new DisplayMode(640, 480, 24, 0),
		new DisplayMode(640, 480, 16, 0),
	};
	
	private boolean running;
	protected Screen s;
	
	// Stop method
	public void stop() {
		running = false;
	}
	
	// Call init and gameloop
	public void run() {
		//try{
			init();
			gameLoop();
//		}finally{
//			s.restoreScreen();
//		}
	}
	
	// Set to full screen
	public void init() {
		s = new Screen("Quantum Man",800,600);
		running = true;		
	}
	
	// Main gameLoop
	public void gameLoop() {
		long startTime = System.currentTimeMillis();
		long cumTime = startTime;
		
		while(running) {
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			
			update(timePassed);
			
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception e){}
			
		}
	}
	
	// Update animation
	public void update(long timePassed) {		
	}
	
	// Draws to the screen
	public abstract void draw(Graphics2D g);
}
