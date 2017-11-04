package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * </p>Satisfied when the ship's shields are raised.  Written as "shields raised".</p>
 * @author 13Clocks
 * */
public class ShieldsRaisedCondition implements Condition {

	public static final Pattern pattern = Pattern.compile(".*shields (are )?(?:up|raised).*");
	
	@Override
	public boolean satisfied(ArtemisPlayer player) {
		if(player.getShieldsState() == null) return false; //Guard against NullPointerExceptions.
		return player.getShieldsState().getBooleanValue();
	}

}
