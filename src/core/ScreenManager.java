package core;


import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class ScreenManager {

	private GraphicsDevice vc;

	public ScreenManager(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = e.getDefaultScreenDevice();
	}

	// get all compatible DisplayModes
	public DisplayMode[] getCompatibleDisplayModes(){
		return vc.getDisplayModes();
	}

	// compares DM passed in to vc DM and see if they match
	public DisplayMode findFirstCompatableMode(DisplayMode[] modes){
		DisplayMode[] goodModes = vc.getDisplayModes();
		for(int x=0;x<modes.length;x++){
			for(int y = 0;y<goodModes.length;y++){
				if(displayModesMatch(modes[x], goodModes[y])){
					return goodModes[y];
				}
			}
		}
		return null;
	}

	// Check if two modes match each other
	private boolean displayModesMatch(DisplayMode first,
			DisplayMode second) {
		if(first.getWidth() != second.getWidth() || first.getHeight() != second.getHeight()){
			return false;
		}
		if(first.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && second.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && first.getBitDepth() != second.getBitDepth()){
			return false;
		}
		if(first.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && second.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && first.getRefreshRate() != second.getRefreshRate()){
			return false;
		}
		return true;
	}

	// get current DM
	public DisplayMode getCurrentDisplayMode(){
		return vc.getDisplayMode();
	}

	// make frame fullscreen
	public void setFullScreen(DisplayMode dm){
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		vc.setFullScreenWindow(f);

		if(dm != null && vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception e){e.printStackTrace();}
		}
		f.createBufferStrategy(2);
	}

	// set graphics object = to this return
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();

		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			return (Graphics2D)s.getDrawGraphics();
		}else{
			return null;
		}
	}


	// update display
	public void update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()){
				s.show();
			}
		}
	}

	// returns full screen window
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}

	// get window dims
	public int getWidth(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getWidth();
		}else{
			return 0;
		}
	}
	public int getHeight(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getHeight();
		}else{
			return 0;
		}
	}
	
	// get out of fullscreen
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	
	// create image compatible with monitor
	public BufferedImage createCompatibleImage(int w, int h, int t){
		Window win = vc.getFullScreenWindow();
		if(win != null){
			GraphicsConfiguration gc = win.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, t);
		}else{
			return null;
		}
	}
}







