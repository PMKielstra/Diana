package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Satisfied when the ship is docked.  Written as "ship is docked".</p>
 * @author 13Clocks
 * */
public class ShipDockedCondition implements Condition {
	
	public static final Pattern pattern = Pattern.compile(".*dock.*");

	@Override
	public boolean satisfied(ArtemisPlayer player) {
		return player.getDockingBase() > -1; //getDockingBase() returns -1 if the ship is not docked.
	}


}
