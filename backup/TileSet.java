//import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * This class contains all useful information and methods for a tileset. This tileset consists
 * of a single source image and the single tiles are stored in a cache when created.
 * 
 * @author Emil
 *
 */
public class TileSet {

	private BufferedImage image;
	private HashMap<Integer,Image> tiles;
	private int width,height,cols;

	/**
	 * The constructor for the TileSet class, it requres the source image and the width and height
	 * of the tiles.
	 * 
	 * @param img The source image, from which the tiles are created.
	 * @param tile_width The width of the tiles.
	 * @param tile_height The height of the tiles.
	 */
	public TileSet(BufferedImage img, int tile_width, int tile_height){
		image = img;
		width = tile_width;
		height = tile_height;
		tiles = new HashMap<Integer,Image>();

		cols = image.getWidth(null) / width;
	}

	/**
	 * If the wanted tile is not already created it will be created from the source image and returned.
	 * 
	 * @param index The index of the tile in the source image. This is 0 for the upper-left tile, 1 for the
	 * next one on the right and so on.
	 * @return The tile (image) from the given index on the source image.
	 */
	public Image getImage(int index) {
		int x = index % cols;
		int y = index / cols;
		if(!tiles.containsKey(index)){
			BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = temp.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.drawImage(image, 0, 0, width, height, x*width, height*y, width*x+width, height*y+height, null);
			g.dispose();
			
			// maing alpha
		    for(int i = 0; i < temp.getHeight(); i++) {  
		        for(int j = 0; j < temp.getWidth(); j++) {  
		            if(temp.getRGB(j, i) == Color.WHITE.getRGB()) {  
		            temp.setRGB(j, i, 0x8F1C1C);  
		            }  
		        }  
		    }  
			tiles.put(index, temp);
		}
		return tiles.get(index);
	}
	
	/**
	 * Creates an animation from a selection of tiles and a duration for each tile to be showed (in milliseconds).
	 * 
	 * @param images An array of integers corresponding to indexes on the source TileSet.
	 * @param durations An array of integers corresponding to the number of milliseconds the tile should be shown.
	 * @return An animation with the wanted tiles shown in the given order and for the given durations. If given invalid
	 * images or durations it returns null.
	 */
	public Animation createAnimation(int[] images, int[] durations){
		if(images.length == durations.length){
		Animation temp = new Animation();
		
		for(int img = 0 ; img<images.length ; img++){
			temp.addScene(getImage(img), durations[img]);
		}
		
		return temp;
		}else{
			return null;
		}
	}
}
