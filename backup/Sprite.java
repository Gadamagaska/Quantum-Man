

import java.awt.Image;
import java.awt.Point;

public class Sprite {
	private Animation a;
	private Point position;
	private float dx,dy;
	
	// constructor
	public Sprite(Animation a){
		this.a = a;
	}
	
	// change position
	public void update(long timePassed){
		position.x += dx*timePassed;
		position.y += dy*timePassed;
		
		a.update(timePassed);
	}
	
	// get x position
	public float getX(){
		return position.x;
	}
	
	// get y position
	public float getY(){
		return position.y;
	}
	
	// set x position
	public void setX(int x){
		position.x = x;
	}
	
	// set y position
	public void setY(int y){
		position.y = y;
	}
	
	// get sprite width
	public int getWidth(){
		return a.getImage().getWidth(null);
	}
	
	// get sprite height
	public int getHeight(){
		return a.getImage().getHeight(null);
	}
	
	// get sprite dx
	public float getDX(){
		return dx;
	}
	
	// get sprite dy
	public float getDY(){
		return dy;
	}
	
	// set sprite dx
	public void setDX(float dx){
		this.dx = dx;
	}
	
	// set sprite dy
	public void setDY(float dy){
		this.dy = dy;
	}
	
	// get sprite image
	public Image getImage(){
		return a.getImage();
	}
	
	// get sprite position
	public Point getPosition(){
		return position;
	}
	
	// set sprite position
	public void setPosition(Point position){
		this.position = position;
	}
}
