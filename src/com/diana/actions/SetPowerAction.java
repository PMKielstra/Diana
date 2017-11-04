package com.diana.actions;

import java.util.regex.Pattern;

import com.diana.main.Action;
import com.diana.main.SystemSelector;
import com.walkertribe.ian.enums.ShipSystem;
import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.protocol.core.eng.EngSetEnergyPacket;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Set system power.  Written as "set torpedo power to 200%".</p>
 * @author 13Clocks
 * */
public class SetPowerAction implements Action {

	public static final Pattern pattern = Pattern.compile(".*((?:primary )beam|torpedo|sensors|maneuver|impulse|warp|jump|front shi?e?ld|rear shi?e?ld)(?: power)? to ([0-9]+)%?");

	private final ShipSystem system;
	private final int percent;

	public SetPowerAction(String system, int percent) {
		this.system = SystemSelector.selectSystem(system);
		this.percent = percent;
	}

	@Override
	public void takeAction(ArtemisNetworkInterface server, ArtemisPlayer player) {
		if(system != null) server.send(new EngSetEnergyPacket(system, percent));
	}

}
