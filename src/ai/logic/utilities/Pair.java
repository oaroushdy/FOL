package ai.logic.utilities;

import ai.logic.utilities.sentence.term.Atom;

public class Pair {
	public Atom variable;
	public Atom substituteBy;

	public Pair(Atom variable, Atom substitueBy) {
		this.variable = variable;
		this.substituteBy = substitueBy;
	}

	public String toString() {
		return substituteBy.toString() + "/" + variable.toString();
	}
}
