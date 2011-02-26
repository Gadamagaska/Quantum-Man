package interfaces;

import java.awt.Image;

/**
 * This interface defines Entities that can be drawed to the screen.
 * That is Entities that simply are not invisible and should be drawn
 * on it's current position.
 * @author Emil
 *
 */
public interface Drawable {
	/**
	 * Should give the current image of an Entity.
	 * @return
	 */
	public Image getImage();
}