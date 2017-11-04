package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * </p>Satisfied when the ship is in reverse.  Written as "ship is reversing" or "reverse".</p>
 * @author 13Clocks
 * */
public class ReverseCondition implements Condition {
	
	public static final Pattern pattern = Pattern.compile(".*revers(?:e|ing)*");

	@Override
	public boolean satisfied(ArtemisPlayer player) {
		if(player.getReverseState() == null) return false; //Guard against NullPointerExceptions.
		return player.getReverseState().getBooleanValue();
	}


}
