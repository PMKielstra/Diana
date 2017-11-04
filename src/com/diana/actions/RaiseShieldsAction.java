package com.diana.actions;

import java.util.regex.Pattern;

import com.diana.main.Action;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.ToggleShieldsPacket;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Raise shields.  Written as "raise shields" or "shields up".</p>
 * @author 13Clocks
 * */
public class RaiseShieldsAction implements Action{
	
	public static Pattern pattern = Pattern.compile(".*(?:raise shields|shields up).*");

	@Override
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player) {
		if(player.getShieldsState() == null) return; //Guard against NullPointerExceptions.
		if(player.getShieldsState().getBooleanValue() == false) //Only raise shields if they are not already raised.
			server.send(new ToggleShieldsPacket());
	}
	
}
