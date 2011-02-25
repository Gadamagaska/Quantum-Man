package entities;

import interfaces.Drawable;

import java.awt.Image;
import java.awt.Point;

public class ImageEntity extends Entity implements Drawable{
	
	private Image image;

	public ImageEntity(Image img, Point pos) {
		super(pos);
		image = img;
	}
	
	public Image getImage(){
		return image;
	}
}
