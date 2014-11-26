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

	@Override
	public String toString() {
		String x = "";
		for (Variable v : domain)
			x += v.toString() + ",";
		x.substring(0, x.length() - 1);
		return Common.UNIVERSAL_QUANTIFIER + x + "[" + sentence.toString()
				+ "]";
	}

}
