import java.awt.Image;
import java.awt.Point;


public class Player {

	private Sprite player;
	private Direction direction;
	private Animation animation;
	private Point position;
	private Image image;
	private Point ghostPoint;
	private Point ghostActualPosition;

	public Player(Animation animation, Direction direction, Point position) {
		setDirection(direction);
		setPosition(position);
		this.animation = animation;
		player = new Sprite(animation);
		this.image = animation.getImage();
		ghostPoint = position.getLocation();
		ghostActualPosition = position;
	}

	public Sprite getSprite() {
		return player;
	}

	public void setDirection(Direction direction){
		this.direction = direction;
	}

	public void setPosition(Point position) {
		this.position = position;
	}	

	public Direction getDirection() {
		return direction;
	}

	public Point getPosition() {
		return position;
	}
	public int getX() {
		return position.getLocation().x;
	}

	public int getY() {
		return position.getLocation().y;
	}

	public Animation getAnimation() {
		return animation;
	}	

	public Image getImage() {
		image = animation.getImage();
		return image;
	}

	public int getGhostX(){
		return ghostPoint.getLocation().x;
	}

	public int getGhostY(){
		return ghostPoint.getLocation().y;
	}

	public Point getGhostActualPoint() {
		return ghostActualPosition;
	}

	public synchronized void movePlayer(Direction direction){
		switch(direction) {	
		case EAST:
			if(Database.getInstance().isWalkable(0, getX()+1, getY())){
				position.x += 1;
				if(getX() >= 7 && ghostPoint.x <= Database.getInstance().getLevelDimensions(0).width-4){
					ghostPoint.x += 1;
					if(getX() - ghostPoint.x <= 3) {
						position.x = 7;
					}
				}}; break;
		case WEST:
			if(Database.getInstance().isWalkable(0, getX()-1, getY())){
				position.x -= 1;
				if(getX() < 2 && ghostPoint.x >= 5){
					ghostPoint.x -= 1;
					if(ghostPoint.x - getX() >= -3){
						position.x = 2;
					}
				}}; break;
		case NORTH:
			if(Database.getInstance().isWalkable(0, getX(), getY()-1)){
				position.y -= 1;
				if(getY() < 2 && ghostPoint.y >= 5){
					ghostPoint.y -= 1;
					if(ghostPoint.y - getY() >= -3){
						position.y = 2;
					}
				}}; break;
		case SOUTH:
			if(Database.getInstance().isWalkable(0, getX(), getY()+1)){
				position.y += 1;
				if(getY() >= 7 && ghostPoint.y <= 4){ //TODO: Set "Database.getInstance().getLevelHeight(0)-5"
					ghostPoint.y += 1;
					if(getY() - ghostPoint.y <= 3) {
						position.y = 7;
					}		
				}}; break;
		}
	}
}
