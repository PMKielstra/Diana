package com.diana.main;

import com.diana.conditions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.diana.actions.*;

/**
 * <p>Takes the user-written rulesets and converts them into a Ruleset object.</p>
 * <p>This class strips out comments (denoted by # and lasting to the end of the line) and blank lines on its own, as well as keeping track of what is a condition and what is an action, but relies on ActionParser and ConditionParser to actually generate Actions and Conditions.</p>
 * @author 13Clocks
 * */
public class RulesetParser {
	
	private static enum State {CONDITIONS, ACTIONS}
	private State state = State.CONDITIONS; //Are we currently reading a list of conditions or a list of actions?  This is important because "red alert", say, could be a condition or an action.
	
	private ActionParser actions = new ActionParser(); //Parsers for actions and conditions.
	private ConditionParser conditions = new ConditionParser();
	
	private Pattern comments = Pattern.compile("(.*?)#.*"); //Regex to recognise comments.
	
	/**
	 * Parse a chunk of text into a ruleset, gracefully skipping any malformed lines or rules with no conditions or actions.
	 * @param text The raw text that the user inputs.
	 * @return A ruleset.  It might not have any rules in it, but it will not be null.
	 * */
	public Ruleset parseRuleset(String text) {
		Rule currentRule = new Rule();
		Ruleset ruleset = new Ruleset();
		String[] lines = text.split("\\r?\\n"); //Split the text into lines, supporting either \n or by \r\n.
		for(int i = 0; i < lines.length; i++) { //Iterate over each line.
			String line = lines[i].toLowerCase(); //Convert to lowercase: SHIELDS is the same as shields is the same as ShIeLdS.
			Matcher commentMatcher = comments.matcher(line); //Check for EOL comments.
			if(commentMatcher.matches()) { //If there is one,
				line = commentMatcher.group(1).trim(); //Cut it out and remove whitespace.
			}else { //Otherwise,
				line = line.trim(); //Remove whitespace anyway.
			}
			if(line.length() == 0) continue; //Skip empty lines.  They won't cause errors, but they will take up processing time if they're fed through the regex-based parsers.
			if(line.equals("if")) { //If this is the beginning of an IF block,
				state = State.CONDITIONS; //We are now looking for conditions.
				if(currentRule.conditions.size() > 0 && currentRule.actions.size() > 0) ruleset.rules.add(currentRule); //If there are both conditions and actions in the rule we've been parsing up until now, then put it into the ruleset.
				currentRule = new Rule(); //All rules start with IF, and IF always starts a rule, so create a new rule.
				continue; //No need to parse anything more.
			}
			if(line.equals("then")) { //If this is the beginning of a THEN block,
				state = State.ACTIONS; //We are now looking for actions.
				continue; //No need to parse anything more.
			}
			switch(state) { //This isn't IF or THEN, so it must be a condition or an action.  We use a switch statement in case we later want to add more types of state.
			case CONDITIONS: //If we're looking for conditions,
				Condition c = conditions.parseCondition(line); //Try to parse the line as a condition.
				if(c != null) currentRule.conditions.add(c); //If it isn't null (it's a recognised condition), add it to the rule.
				break;
			case ACTIONS: //If we're looking for actions,
				Action a = actions.parseAction(line); //Try to parse the line as an action.
				if(a != null) currentRule.actions.add(a); //If it isn't null (it's a recognised action), add it to the rule.
				break;
			}
		}
		if(currentRule.conditions.size() > 0 && currentRule.actions.size() > 0) ruleset.rules.add(currentRule); //The end of the final rule isn't signalled by the next one's IF, because there is no next one by definition.  So, we have to add it to the ruleset manually if it's got conditions and actions.
		return ruleset; //Return the ruleset.
	}
}