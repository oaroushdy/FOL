package ai.logic.utilities.sentence.term.atom;

import ai.logic.utilities.sentence.term.Atom;

public class Variable extends Atom {
	public String value;
	public boolean isNegative;

	public Variable(String value, boolean isNegative) {
		this.value = value;
		this.isNegative = isNegative;
	}
}
