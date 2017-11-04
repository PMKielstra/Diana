package com.diana.main;

import com.walkertribe.ian.enums.ShipSystem;

/**
 * <p>A helper class to parse text into ShipSystem objects.  Used to read engineering rules ("set torpedo power to 100").</p>
 * @author 13Clocks
 * */
public class SystemSelector {
	
	/**
	 * <p>Iterate over all known names for ship systems and return the corresponding ShipSystem.</p>
	 * @param name The text name of the system.
	 * @return The corresponding ShipSystem, or null if none were found.
	 * */
	public static ShipSystem selectSystem(String name) {
		switch(name) {
		case "beam":
		case "primary beam":
			return ShipSystem.BEAMS;
		case "torpedo":
			return ShipSystem.TORPEDOES;
		case "sensors":
			return ShipSystem.SENSORS;
		case "maneuver":
			return ShipSystem.MANEUVERING;
		case "impulse":
			return ShipSystem.IMPULSE;
		case "warp":
		case "jump":
			return ShipSystem.WARP_JUMP_DRIVE;
		case "front shield":
		case "front shld":
			return ShipSystem.FORE_SHIELDS;
		case "rear shield":
		case "rear shld":
			return ShipSystem.AFT_SHIELDS;
		default:
			return null;
		}
	}
}
