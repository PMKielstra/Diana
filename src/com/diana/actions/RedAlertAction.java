package com.diana.actions;

import java.util.regex.Pattern;

import com.diana.main.Action;
import com.walkertribe.ian.enums.AlertStatus;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.comm.ToggleRedAlertPacket;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Go to red alert.  Written as "red alert".</p>
 * @author 13Clocks
 * */
public class RedAlertAction implements Action {

	public static Pattern pattern = Pattern.compile(".*red alert.*");
	
	@Override
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player) {
		if(player.getAlertStatus().equals(AlertStatus.NORMAL)) server.send(new ToggleRedAlertPacket());
	}

}
