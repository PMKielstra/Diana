package com.diana.conditions;

import java.util.regex.Matcher;

import com.diana.main.Condition;

/**
 * <p>Parses user-written strings into conditions ("ship is at red alert", "energy is below 100").</p>
 * @see Condition
 * @author 13Clocks
 * */
public class ConditionParser {
	
	/**
	 * <p>Uses a series of regular expressions to parse text.</p>
	 * @param text The string to parse.
	 * @return A Condition, or null if the text didn't match any known condition.
	 * */
	public Condition parseCondition(String text) {
		Matcher m;
		if(RedAlertCondition.pattern.matcher(text).matches()) {
			return new RedAlertCondition();
		}else if(ReverseCondition.pattern.matcher(text).matches()){
			return new ReverseCondition();
		}else if(ShieldsRaisedCondition.pattern.matcher(text).matches()) {
			return new ShieldsRaisedCondition();
		}else if(ShipDockedCondition.pattern.matcher(text).matches()) {
			return new ShipDockedCondition();
		}else if(WarpCondition.pattern.matcher(text).matches()){
			return new WarpCondition();
		}else if((m = LowEnergyCondition.pattern.matcher(text)).matches()) {
			return new LowEnergyCondition(Integer.parseInt(m.group(1)));
		}
		return null;
	}
}
