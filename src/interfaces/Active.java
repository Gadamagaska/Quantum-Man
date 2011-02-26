package interfaces;

/**
 * This interface contains the method for activating an
 * Entity in the game-world, this means that classes that
 * implement this has to define some code that is run when
 * the local player activates it (by clicking on it or smth).
 * @author Emil
 *
 */
public interface Active {
	/**
	 * The method that is run when the Entity is activated by
	 * the local Player.
	 */
	public void activate();
}
