package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.enums.AlertStatus;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Satisfied when the ship is at red alert.  Written as "red alert".</p>
 * @author 13Clocks
 * */
public class RedAlertCondition implements Condition {
	
	public static final Pattern pattern = Pattern.compile(".*red alert.*");

	@Override
	public boolean satisfied(ArtemisPlayer player) {
		if(player.getAlertStatus() == null) return false; //Guard against NullPointerExceptions.
		return player.getAlertStatus().equals(AlertStatus.RED);
	}

}
