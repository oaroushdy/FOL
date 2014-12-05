package ai.logic.utilities.sentence.term.quantifier;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.Common;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.term.Quantifier;
import ai.logic.utilities.sentence.term.atom.Variable;

public class AQuantifier extends Quantifier implements CF {

	public AQuantifier(Sentence sentence, ArrayList<Variable> domain,
			boolean isNegative) {
		super(sentence, domain, isNegative);
		quantifier = this;
	}

	public AQuantifier(Sentence sentence, ArrayList<Variable> domain) {
		super(sentence, domain, false);
		quantifier = this;
	}

	public AQuantifier(Sentence s, Variable domain, boolean isNegative) {
		super(s, domain, isNegative);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	public AQuantifier(Sentence s, Variable domain) {
		super(s, domain, false);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		if (quantifier instanceof EQuantifier)
			return ((EQuantifier) quantifier).toString();
		if (quantifier.getClass().equals(Sentence.class))
			return ((Sentence) quantifier).toString();
		if (domain == null)
			return ((AQuantifier) quantifier).sentence.toString();
		String x = "";
		if (((AQuantifier) quantifier).isNegative)
			x += Common.NOT;
		for (Variable v : ((AQuantifier) quantifier).domain)
			x += v.toString() + ",";
		x = x.substring(0, x.length() - 1);
		return Common.UNIVERSAL_QUANTIFIER + x + "["
				+ ((AQuantifier) quantifier).sentence.toString() + "]";
	}
	public void Step5Skolomize(ArrayList<Variable> currentDomain) {
		// TODO Auto-generated method stub
		super.Step5Skolomize(currentDomain);
		if (quantifier instanceof EQuantifier) {
			((EQuantifier) quantifier).Step5Skolomize(currentDomain);
			return;
		}
		sentence.Step5Skolomize(currentDomain);
	}

}
