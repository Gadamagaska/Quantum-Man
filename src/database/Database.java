package database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import core.Animation;
import entities.Entity;

/**
 * The database contains all non-code data needed for the game to run. It is written
 * in a singleton pattern, meaning there is only one instance of this object and it
 * can be referenced from anywhere through its static .getInstance() method.
 * 
 * Upon creation the database will load all text-data from the data folder and create
 * objects to handle images. The database has methods for reaching into these sub-objects
 * and get the wanted data.
 * 
 * @author Emil
 *
 */
public class Database {

	private static Database me = new Database();

	public File image_dir = new File("images");
	public File data_dir = new File("data");
	public File tile_dir = new File(image_dir,"tilesets");

	// Loaded data
	private HashMap<Integer, Level> levels;
	private HashMap<String,TileSet> tilesets;

	/**
	 * The constructor for the database loads all text-data and creates
	 * objects to handle image-data.
	 */
	private Database(){
		levels = new HashMap<Integer,Level>();
		tilesets = new HashMap<String,TileSet>();

		loadTileSets();
		loadLevels();
		System.out.println("Database loaded");
	}

	/**
	 * Loads all tileset (PNG) in the \images\tileset\ with the information specified
	 * in the text file with the same name.
	 */
	private void loadTileSets() {
		File[] tile_files = tile_dir.listFiles();
		File image_file = null;
		for(File f : tile_files){
			if(f.getName().endsWith(".png")){
				image_file = f;
			}else if(f.getName().endsWith(".txt")){
				// load the width and height from it
				int width = 0;
				int height = 0;
				ArrayList<int[]> numbers = new ArrayList<int[]>();
				BufferedReader text = null;
				String currentType = null;
				String line = null;
				String[] sep = null;
				try {
					text = new BufferedReader(new FileReader(f));
					while((line = text.readLine()) != null){
						if((sep = line.split("=")).length > 1){
							if(sep[0].equals("WIDTH")){
								width = Integer.parseInt(sep[1]);
							}else if(sep[0].equals("HEIGHT")){
								height = Integer.parseInt(sep[1]);
							}else if(sep[0].equals("WALKABLE")){
								currentType = sep[0];
							}
						}else{
							if(currentType.equals("WALKABLE")){
								sep = line.split(",");
								int[] tal = new int[sep.length];
								for(int i = 0 ; i < sep.length ; i++){
									tal[i] = Integer.parseInt(sep[i]);
								}
								numbers.add(tal);
							}
						}
					}

					// load the image (with same name)
					// create tileset with width and height and add to Map
					if(width != 0 && height != 0 && image_file != null){
						String name = new StringTokenizer(f.getName(),".").nextToken();
						TileSet current = loadTile(name,width,height);
						current.addWalkable(numbers);
						tilesets.put(name, current);

						image_file = null;
					}else{
						System.out.println("Not enough info for TileSet="+image_file.getName());
					}
				} catch (Exception e) {e.printStackTrace();}
			}
		}
	}

	/**
	 * The private method for loading a specific TileSet into the database.
	 * 
	 * @param name The name of the PNG file in the images/tilesets folder to be loaded.
	 * @param w The width of the tiles in the image.
	 * @param h The height of the tiles in the image.
	 */
	private TileSet loadTile(String name,int w, int h) {
		BufferedImage img = null;

		try{
			img = ImageIO.read(new File(tile_dir,name+".png"));
		}catch(IOException e){
			e.printStackTrace();
		}

		return new TileSet(img,w,h);
	}

	/**
	 * The singleton method for getting the instantiated object of the database.
	 * 
	 * @return The database object.
	 */
	public static Database getInstance(){
		return me;
	}

	/**
	 * The method that loads the text-files with information about the different levels.
	 * This includes the different layers of tiles and the walkability of the tiles on the
	 * used TileSet.
	 */
	private void loadLevels() {
		File[] level_files = data_dir.listFiles();

		ArrayList<int[]> complete_layer = null;
		int[] tile_row;

		BufferedReader br;
		FileReader fr;
		String cur_type = "";
		String line;
		String[] splitted_line;
		int layer = 0;
		Level cur = null;
		for(int level = 0 ; level<level_files.length ; level++){
			if(level_files[level].getName().startsWith("level")){
				// create the level
				int lvl_num = Integer.parseInt(level_files[level].getName().replaceFirst("level", "").replaceFirst(".txt", ""));
				cur = new Level(lvl_num);
				levels.put(lvl_num,cur);
				try {
					fr = new FileReader(level_files[level]);
					br = new BufferedReader(fr);
					boolean done = false;
					while(!done){
						//System.out.println(cur_type);
						line = br.readLine();
						if(line != null){
							// Er det en header?
							if((splitted_line = line.split("=")).length > 1){
								// Gem forrige load
								if(complete_layer != null){
									save(cur_type,cur,layer,complete_layer);
								}

								// one-liners
								if(splitted_line[0].equals("TILESET")){
									cur.setTileSet(tilesets.get(splitted_line[1]));
								}else{
									// Gem nuværende header
									cur_type = splitted_line[0];
									layer = Integer.parseInt(splitted_line[1]);
								}

							}else if(cur_type.equals("LAYER")){
								splitted_line = line.split(",");
								tile_row = new int[splitted_line.length];

								for(int num = 0 ; num < splitted_line.length ; num++){
									tile_row[num] = Integer.parseInt(splitted_line[num]);
								}

								if(complete_layer == null){
									complete_layer = new ArrayList<int[]>();
								}

								complete_layer.add(tile_row);
							}
						}else{
							save(cur_type,cur,layer,complete_layer);
							cur = null;
							done = true;
						}
					}


				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.err.println("Failed to load level="+level);
				} catch (IOException e){
					e.printStackTrace();
					System.err.println("Failed to read line");
				} catch (NumberFormatException e){
					e.printStackTrace();
					System.err.println("Invalid integer number");
				}
			}
		}
	}

	/**
	 * The private method to save a given layer or walkability array to a level-object.
	 * 
	 * @param cur_type Tells if the array to be saved is a layer or an array of walkable tiles.
	 * @param cur The Level to save the data in.
	 * @param layer If the cur_type is a layer, the Level needs specified what number the layer is.
	 * @param complete_layer The ArrayList containing the different lines (rows) in the array.
	 */
	private void save(String cur_type,Level cur,int layer, ArrayList<int[]> complete_layer) {
		if(complete_layer != null){
			int[][] total_layer = new int[complete_layer.size()][complete_layer.get(0).length];
			for(int index = 0 ; index < total_layer.length ; index++){
				total_layer[index] = complete_layer.get(index);
			}
			if(cur_type.equals("LAYER")){
				cur.addLayer(layer,total_layer);
			}
			complete_layer.clear();
			complete_layer = null;
		}
	}

	/**
	 * Finds the number of tile at the position matching the level, layer and x-, y-position.
	 * 
	 * @param level The level to find the tile for.
	 * @param layer The layer of the wanted tile.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return An integer corresponding to an index in the level's TileSet source image.
	 */
	public int getTile(int level, int layer, int x, int y){
		return levels.get(level).getTile(layer, x, y);
	}

	/**
	 * Finds the tile-image of a given TileSet at the given index.
	 * 
	 * @param key The name of the TileSet to search in, this matches the PNG-files name in the
	 * images/tilesets folder.
	 * @param index The index of the tile in the source image. This is 0 for the upper-left tile, 1 for the
	 * next one on the right and so on.
	 * @return The tile (image) from the given index on the source image.
	 */
	public Image getTileImage(String key,int index){
		return tilesets.get(key).getImage(index);
	}

	public Animation getTileAnimation(String key, int[] images, int[] durations){
		return tilesets.get(key).createAnimation(images, durations);
	}

	public Animation getTileAnimation(String key, int[] images, int durations){
		return tilesets.get(key).createAnimation(images, durations);
	}

	/**
	 * Finds the dimensions of a level.
	 * 
	 * @param level The level that needs to be check dimensions of.
	 * @return A Dimension-object containing the width and height of the level.
	 */
	public Dimension getLevelDimensions(int level){
		return levels.get(level).getDimensions();
	}

	/**
	 * Evaluates if the given position of the level is walkable, this involves checking if
	 * any of the layers contains an unwalable tile at the given position.
	 * 
	 * @param level The level number.
	 * @param x The x-coordinate of the level.
	 * @param y The y-coordinate of the level.
	 * @return True if the postion can be walked on, False if it is contains atleast one unwalkable tile.
	 */
	public boolean isWalkable(int level, int x, int y){
		return levels.get(level).isWalkable(x, y);
	}
	
	public HashSet<Entity> getStartEntities(int level){
		return levels.get(level).getStartEntities();
	}
}