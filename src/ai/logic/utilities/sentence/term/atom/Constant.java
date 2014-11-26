package ai.logic.utilities.sentence.term.atom;

import ai.logic.utilities.Common;
import ai.logic.utilities.sentence.term.Atom;

public class Constant extends Atom {
	public String value;

	public Constant(String value, boolean isNegative) {
		this.value = value;
		this.isNegative = isNegative;
	}

	@Override
	public String toString() {
		if (isNegative)
			return Common.NOT + value;
		else
			return value;
	}

	@Override
	public void Step7Expand() {

	}

	@Override
	public void substituteBy(Variable x, Variable y) {
		
	}

	public boolean equals(Constant a) {
		return this.value.equals(a.value);
	}

	public boolean equals(Atom a) {
		if (a.getClass().equals(this.getClass()))
			return this.value.equals(((Constant) a).value);
		return false;
	}
}
