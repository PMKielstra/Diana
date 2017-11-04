package com.diana.main;

import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>A simple interface for things that Diana can do.  This exists mainly to provide a coherent point of entry to the various Action classes so that Diana can call them all the same way.</p>
 * @author 13Clocks.
 * */
public interface Action {
	/**
	 * <p>Called when Diana determines that the conditions have been satisfied for the Action to take place.</p>
	 * @param server The ArtemisNetworkInterface connection to the server.  Used for sending packets.
	 * @param player The ArtemisPlayer object representing the current player state.
	 * */
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player);
}
