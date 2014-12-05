package ai.logic.utilities;

import ai.logic.utilities.sentence.term.atom.Variable;

public class Common {
	// public static final String UNIVERSAL_QUANTIFIER = "\u2200";
	// public static final String EXIST_QUANTIFIER = "\u2203";
	// public static final String NOT = "\u00ac";
	public static final String NOT = "!";
	public static final String UNIVERSAL_QUANTIFIER = "A:";
	public static final String EXIST_QUANTIFIER = "E:";

	public static final int EXPAND_OR = 0;
	public static final int EXPAND_AND = 1;

	public static int counter = 1;
	public static int fcounter = 1;

	public static Variable createNewUnusedVariable() {
		String x = "x" + counter;
		counter++;
		return new Variable(x, false);
	}

}
