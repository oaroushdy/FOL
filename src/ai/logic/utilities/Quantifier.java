package ai.logic.utilities;

import java.util.ArrayList;

public class Quantifier extends Term {

	public Sentence s;
	public ArrayList<Variable> domain;

	public Quantifier(Sentence s, ArrayList<Variable> domain) {
		this.s = s;
		this.domain = domain;
	}
}
