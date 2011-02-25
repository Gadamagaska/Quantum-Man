package enums;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An enum class to get a direction. A random direction can be obtained by calling the getRandomDirection() method.
 * @author BScient
 * 
 */
public enum Direction {
	WEST {},
	EAST {},
	NORTH {},
	SOUTH{};

	/**
	 * Shuffle an ArrayList containing the directions and returns the first value.
	 * @return randomDirection A random of the four directions.
	 */
    public static Direction getRandomDirection()
    {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction d : Direction.values())
            directions.add(d);
        Collections.shuffle(directions);
        Direction randomDirection = directions.get(0);
        
        return randomDirection;
    }
}
