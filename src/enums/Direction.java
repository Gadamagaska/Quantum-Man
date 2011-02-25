package enums;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * En enumklasse til at få Direction. Hver direction kan give en animation, der tilhører.
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
}
