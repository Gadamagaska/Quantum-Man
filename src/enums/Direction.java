package enums;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * An enum class to get a direction. A randomly shuffled ArrayLost containing all directions
 * can be achieved by the shuffleDirections() method.
 * @author BScient
 * 
 */
public enum Direction {
	WEST {},
	EAST {},
	NORTH {},
	SOUTH{};

    public static Collection<Direction> shuffleDirections()
    {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction d : Direction.values())
            directions.add(d);
        Collections.shuffle(directions);
        
        return directions;
    }
    
    public void weHaw() {
    	// Hi2u, Emil!
    }
}
