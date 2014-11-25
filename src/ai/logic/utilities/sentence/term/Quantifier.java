package ai.logic.utilities.sentence.term;

import java.util.ArrayList;

import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.Term;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Quantifier extends Term {

	public Sentence s;
	public ArrayList<Variable> domain;

	public Quantifier(Sentence s, ArrayList<Variable> domain) {
		this.s = s;
		this.domain = domain;
	}
}
