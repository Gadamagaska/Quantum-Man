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

	private ArrayList<int[][]> layers = null;
	private int[][] walkable_tiles = null;

	public Level(){
		layers = new ArrayList<int[][]>();
	}
	
	/**
	 * Loads a layer into the level object.
	 * @param index The layer number.
	 * @param layer The two-dimensional array of integers that is the layer.
	 */
	public void addLayer(int index, int[][] layer){
		layers.add(layer);
	}

	public void addWalkable(int[][] tiles){
		walkable_tiles = tiles.clone();
	}

	public int getTile(int layer, int x, int y){
		return layers.get(layer)[y][x];
	}

	public Dimension getDimensions(){
		return new Dimension(layers.get(0)[0].length, layers.get(0).length);
	}

	public boolean isWalkable(int x, int y){
		Dimension dim = getDimensions();
		if(x < 0 || x >= dim.width || y < 0 || y >= dim.height){
			System.out.println("out of bounds");
			return false;
		}
		int num;
		for(int layer = 0 ; layer < layers.size() ; layer++){
			num = getTile(layer,x,y);
			if(walkable_tiles[num/10][num%10] == 1){
				return false;
			}
		}
		return true;
	}
}