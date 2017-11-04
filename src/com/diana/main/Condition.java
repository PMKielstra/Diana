package com.diana.main;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>A simple interface for things that Diana can sense. This exists mainly to provide a coherent point of entry to the various Condition classes so that Diana can call them all the same way.</p>
 * @author 13Clocks
 * */
public interface Condition {
	/**
	 * <p>Called every update to determine if the condition is satisfied.  Once all conditions in a Rule have been satisfied, Diana executes every action in that Rule in order.</p>
	 * @param player The ArtemisPlayer representing the current player state.
	 * */
	public boolean satisfied(ArtemisPlayer player);
}
