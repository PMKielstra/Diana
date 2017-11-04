package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Satisfied when the ship is at warp.  Written as "ship is at warp".</p>
 * @author 13Clocks
 * */
public class WarpCondition implements Condition{
	
	public static final Pattern pattern = Pattern.compile(".*warp.*");

	@Override
	public boolean satisfied(ArtemisPlayer player) {
		return player.getWarp() > 0;
	}

}
