package entities;

import interfaces.Drawable;

import java.awt.Image;
import java.awt.Point;

/**
 * Defines an Entity that should be visible on the screen through an Image.
 * @author Emil
 *
 */
public class ImageEntity extends Entity implements Drawable{
	
	private Image image;

	/**
	 * @param img The Image of the ImageEntity.
	 * @param pos The starting postion of the ImageEntity.
	 */
	public ImageEntity(Image img, Point pos) {
		super(pos);
		image = img;
	}
	
	@Override
	public Image getImage(){
		return image;
	}
}
