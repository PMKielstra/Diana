package com.diana.main;
import java.util.ArrayList;
import java.util.List;

import com.walkertribe.ian.iface.ArtemisNetworkInterface;
import com.walkertribe.ian.world.ArtemisPlayer;

/**
 * <p>This is what user input eventually gets turned into: a list of conditions, a list of actions, and a small amount of code to run the latter if the former is satisfied.  These should never be manipulated on their own.  They only exist as part of a Ruleset.</p>
 * <p>Rules only run when they have just gone from unsatisfied to satisfied.  Otherwise, the "IF red alert THEN raise shields" rule would try to raise shields every time an update packet came in while red alert was active.  This would lead to hundreds of attempts at raising shields during any given battle, which would be inefficient and could jam weaker networks.</p>
 * @see Ruleset
 * @see RulesetParser
 * @author 13Clocks
 * */
public class Rule {
	private boolean previouslySatisfied = false; //If this is true, the rule has just run and should not be run again.
	/**
	 * <p>Conditions for the rule to check.</p>
	 * */
	public List<Condition> conditions = new ArrayList<Condition>();
	/**
	 * <p>Actions for the rule to carry out.</p>
	 * */
	public List<Action> actions = new ArrayList<Action>();
	/**
	 * <p>Checks every condition and, if they are satisfied and the rule has not already run, carries out every action.</p>
	 * @param server The ArtemisNetworkInterface to pass to the Actions.
	 * @param player The ArtemisPlayer to pass to the Conditions and Actions.
	 * @see Condition
	 * @see Action
	 * */
	public void run(ArtemisNetworkInterface server, ArtemisPlayer player) {
		for(Condition condition: conditions) { //For each condition,
			if(!condition.satisfied(player)) { //If it's not satisfied,
				previouslySatisfied = false; //The rule has not just been carried out.
				return; //Quit -- no need to check all conditions if the first is unsatisfied.
			}
		}
		//By this point, obviously, all conditions have been satisfied.
		if(!previouslySatisfied) { //If the rule has not just been carried out,
			previouslySatisfied = true; //Record that it has,
			for(Action action: actions) { //And carry out all actions.
				action.takeAction(server, player);
			}
		}
	}
}
