package ai.logic.utilities.sentence.term.atom;

import ai.logic.utilities.Common;
import ai.logic.utilities.sentence.term.Atom;

public class Variable extends Atom {
	public String value;

	public Variable(String value, boolean isNegative) {
		this.value = value.toLowerCase();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void substituteBy(Variable x, Variable y) {
		if (this.equals(x))
			value = y.value;
	}

	public boolean equals(Variable x) {
		return value.equals(x.value);
	}

	public boolean equals(Atom a) {
		if (a.getClass().equals(this.getClass()))
			return this.value.equals(((Variable) a).value);
		return false;
	}
}
