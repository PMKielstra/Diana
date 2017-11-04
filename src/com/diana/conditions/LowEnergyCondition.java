package com.diana.conditions;

import java.util.regex.Pattern;

import com.diana.main.Condition;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>Satisfied when the ship is below a certain amount of energy.  Written as "Energy is below 200".</p>
 * @author 13Clocks
 * */
public class LowEnergyCondition implements Condition {
	
	public static final Pattern pattern = Pattern.compile(".*energy (?:is )?below ([0-9]+).*");
	
	private final int threshold;
	
	/**
	 * @param threshold The minimum energy that will not trigger this condition.  (The comparison is "less than", not "less than or equal to".)
	 * */
	public LowEnergyCondition(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean satisfied(ArtemisPlayer player) {
		return player.getEnergy() < threshold; //Is the player's energy below the threshold?
	}

}
