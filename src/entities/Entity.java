package entities;

import java.awt.Point;

import enums.Direction;

public class Entity {
	
	private Point pos;
	private String name;
	
	public Entity(Point position){
		pos = position;
		name = "";
	}
	
	public Point getPos(){
		return pos;
	}
	
	public int getX(){
		return pos.x;
	}
	
	public int getY(){
		return pos.y;
	}
	
	public void move(int x, int y){
		pos.x = x;
		pos.y = y;
	}
	
	public void move(Direction dir){
		switch(dir){
		
		case NORTH:{
			pos.y -= 1;
			break;
		}
		case SOUTH:{
			pos.y += 1;
			break;
		}
		case WEST:{
			pos.x -= 1;
			break;
		}
		case EAST:{
			pos.x += 1;
			break;
		}
		}
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
