package com.diana.actions;

import java.util.regex.Matcher;

import com.diana.main.Action;

/**
 * <p>Parses user-written strings into actions ("go to red alert", "raise shields").</p>
 * @see Action
 * @author 13Clocks
 * */
public class ActionParser {
	
	/**
	 * <p>Uses a series of regex Patterns to parse text.</p>
	 * @param text The string to parse.
	 * @return An Action, or null if the text didn't match any known action.
	 * */
	public Action parseAction(String text) {
		Matcher m;
		if(RaiseShieldsAction.pattern.matcher(text).matches()) {
			return new RaiseShieldsAction();
		}else if(RequestDockAction.pattern.matcher(text).matches()) {
			return new RequestDockAction();
		}else if(RedAlertAction.pattern.matcher(text).matches()) {
			return new RedAlertAction();
		}else if((m = SetPowerAction.pattern.matcher(text)).matches()) {
			return new SetPowerAction(m.group(1), Integer.parseInt(m.group(2)));
		}else if((m = SetCoolantAction.pattern.matcher(text)).matches()) {
			return new SetCoolantAction(m.group(1), Integer.parseInt(m.group(2)));
		}
		return null;
	}
	
}
