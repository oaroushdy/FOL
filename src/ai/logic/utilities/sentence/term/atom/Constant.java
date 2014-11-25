package ai.logic.utilities.sentence.term.atom;

import ai.logic.utilities.sentence.term.Atom;

public class Constant extends Atom {
	public String value;
	public boolean isNegative;

	public Constant(String value, boolean isNegative) {
		this.value = value;
		this.isNegative = isNegative;
	}
}
