package ai.logic.utilities.sentence.term.quantifier;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.Common;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.term.Quantifier;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class EQuantifier extends Quantifier implements CF {

	public EQuantifier(Sentence s, ArrayList<Variable> domain,
			boolean isNegative) {
		super(s, domain, isNegative);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String x = "";
		for (Variable v : domain)
			x += v.toString() + ",";
		x.substring(0, x.length() - 1);
		return Common.EXIST_QUANTIFIER + x + "[" + sentence.toString() + "]";
	}

	@Override
	public void Step5Skolomize() {
		// TODO Auto-generated method stub
		Predicate y = Common.createNewUnusedPredicate(domain);
		for (Variable x : domain) {
			sentence.substituteBy(x, y);
		}
	}

	public void substituteBy(Variable x, Predicate y) {
		y = new Predicate(y, domain); // append domain to current domains
		beforeConnector.substituteBy(x, y);
		afterConnector.substituteBy(x, y);
	}

}
