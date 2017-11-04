package com.diana.actions;

import java.util.regex.Pattern;

import com.diana.main.Action;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.helm.HelmRequestDockPacket;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Request dock with any nearby stations.  Written as "dock" or "request dock".</p>
 * <p>Diana does not, and will never, know where the ship is in relation to anything else, so it is entirely possible that this will fail because the ship isn't close enough to a base.</p>
 * @author 13Clocks
 * */
public class RequestDockAction implements Action {

	public static Pattern pattern = Pattern.compile(".*dock.*");
	
	@Override
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player) {
		server.send(new HelmRequestDockPacket());
	}

}
