package interfaces;

/**
 * This interface dictates Entities to implement their
 * own movement AI.
 * @author Emil
 *
 */
public interface MoveAI {
	/**
	 * This should be defined by the Entity to choose it's own movement,
	 * probably with some waiting between each move.
	 * @param timePassed might be useful.
	 */
	public void move(long timePassed);
}
