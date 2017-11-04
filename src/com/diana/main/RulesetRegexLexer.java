package com.diana.main;

import java.util.HashMap;
import java.util.Map;

import jsyntaxpane.TokenType;
import jsyntaxpane.lexers.SimpleRegexLexer;

/**
 * <p>A regex-based lexer for JSyntaxPane.  Highlights IF, THEN, numbers, and the names of systems (maneuver, jump, et cetera).</p>
 * <p>I keep this as its own class mainly for code cleanliness.  It could easily be folded into MainWindow.</p>
 * @author 13Clocks
 * */
public class RulesetRegexLexer extends SimpleRegexLexer {

	public RulesetRegexLexer() {
		super(getProps()); //Call a private function rather than generating this list within the constructor because the super() call must be the first statement.
	}
	
	private static Map<TokenType, String> getProps(){
		Map<TokenType, String> props = new HashMap<TokenType, String>(); //Create a HashMap to contain all the token types.
		props.put(TokenType.KEYWORD, "(?i)(?:IF|THEN)"); //IF and THEN.
		props.put(TokenType.NUMBER, "[0-9.]+%?"); //Numbers and percentages.
		props.put(TokenType.COMMENT, "#.*"); //EOL comments.
		props.put(TokenType.KEYWORD2, "((?:primary )beam|torpedo|sensors|maneuver|impulse|warp|jump|front shi?e?ld|rear shi?e?ld)"); //System names.
		return props;
	}
	
}
