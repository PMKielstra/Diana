package com.diana.main;
import java.util.ArrayList;
import java.util.List;

import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>A helper class to store a list of Rules along with a simple method for carrying them all out.</p>
 * @author 13Clocks
 * @see Rule
 * */
public class Ruleset {
	
	/**
	 * <p>The Rules this ruleset will carry out.</p>
	 * */
	public List<Rule> rules = new ArrayList<Rule>();
	
	/**
	 * <p>Try to carry out each Rule in the ruleset.</p>
	 * @param server The ArtemisNetworkInterface to pass to the Rules.
	 * @param player The player state, as represented by an ArtemisPlayer, to pass to the Rules.
	 * */
	public void carryOut(ArtemisNetworkInterface server, ArtemisPlayer player) {
		for(Rule rule : rules) {
			rule.run(server, player);
		}
	}

}
