package entities;

import java.awt.Image;
import java.awt.Point;

public class ImageEntity extends Entity {
	
	private Image image;

	public ImageEntity(Image img, Point pos) {
		super(pos);
		image = img;
	}
	
	public Image getImage(){
		return image;
	}

	/**
	 * These entities doesn't have any logic movement, feel
	 * free to override in specific cases.
	 */
	@Override
	public void move() {}

	@Override
	public void activate() {}

}
