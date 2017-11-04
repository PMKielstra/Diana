package com.diana.actions;

import java.util.regex.Pattern;

import com.diana.main.Action;
import com.diana.main.SystemSelector;
import com.walkertribe.ian.enums.ShipSystem;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.eng.EngSetCoolantPacket;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Set system coolant.  Written as "set torpedo coolant to 7".</p>
 * <p>This can fail if there is not enough coolant available.</p>
 * @author 13Clocks
 * */
public class SetCoolantAction implements Action {

	public static final Pattern pattern = Pattern.compile(".*((?:primary )beam|torpedo|sensors|maneuver|impulse|warp|jump|front shi?e?ld|rear shi?e?ld) coolant to ([0-9]+)?");

	private final ShipSystem system;
	private final int level;

	public SetCoolantAction(String system, int percent) {
		this.system = SystemSelector.selectSystem(system);
		this.level = percent;
	}

	@Override
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player) {
		if(system != null) server.send(new EngSetCoolantPacket(system, level));
	}

}
