package entities;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * The level contains information about the three layers in a level.
 * In the future it will also hold information about what Entities start
 * where etc etc
 * @author Emil
 *
 */
public class Level {

	public static final int NUM_LAYERS = 3;

	private int[][][] layers = null;
	private int[][] walkable_tiles = null;
	
	/**
	 * Loads a layer into the level object.
	 * @param index The layer number.
	 * @param layer The two-dimensional array of integers that is the layer.
	 */
	public void addLayer(int index, int[][] layer){
		if(layers == null){
			layers = new int[NUM_LAYERS][layer.length][layer[0].length];
		}
		layers[index] = layer;
	}

	@Deprecated
	public void addWalkable(int[][] tiles){
		walkable_tiles = tiles.clone();
	}

	/**
	 * @return The number specified on the given location, this matches a
	 * tile on the level's TileSet.
	 */
	public int getTile(int layer, int x, int y){
		return layers[layer][y][x];
	}

	/**
	 * @return The dimensions of the current map
	 */
	public Dimension getDimensions(){
		return new Dimension(layers[0][0].length, layers[0].length);
	}
	
	/**
	 * Tells if the specified location is walkable or not, that is; there are
	 * no tiles on any layer of the location that isn't walkable.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return True if the location is walkable.
	 */
	public boolean isWalkable(int x, int y){
		Dimension dim = getDimensions();
		if(x < 0 || x >= dim.width || y < 0 || y >= dim.height){
			System.out.println("out of bounds");
			return false;
		}
		int num;
		for(int layer = 0 ; layer < layers.length ; layer++){
			num = getTile(layer,x,y);
			if(walkable_tiles[num/10][num%10] == 1){
				return false;
			}
		}
		return true;
	}
}