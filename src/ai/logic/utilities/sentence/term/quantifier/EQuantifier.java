package ai.logic.utilities.sentence.term.quantifier;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.Common;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.term.Atom;
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

	public EQuantifier(Sentence s, ArrayList<Variable> domain) {
		super(s, domain, false);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	public EQuantifier(Sentence s, Variable domain, boolean isNegative) {
		super(s, domain, isNegative);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	public EQuantifier(Sentence s, Variable domain) {
		super(s, domain, false);
		quantifier = this;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		if (quantifier instanceof AQuantifier)
			return ((AQuantifier) quantifier).toString();
		if (quantifier.getClass().equals(Sentence.class))
			return ((Sentence) quantifier).toString();
		if (domain == null)
			return ((EQuantifier) quantifier).sentence.toString();
		String x = "";
		if (((EQuantifier) quantifier).isNegative)
			x += Common.NOT;
		for (Variable v : ((EQuantifier) quantifier).domain)
			x += v.toString() + ",";
		x = x.substring(0, x.length() - 1);
		return Common.EXIST_QUANTIFIER + x + "["
				+ ((EQuantifier) quantifier).sentence.toString() + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Step5Skolomize(ArrayList<Variable> currentDomain) {
		// TODO Auto-generated method stub
		if (quantifier instanceof AQuantifier) {
			return;
		}

		if (domain == null)
			return;
		if (!currentDomain.isEmpty()) {
			Predicate y;
			for (Variable x : domain) {
				y = createNewUnusedPredicate(currentDomain);// skolo function
//				System.out.println("skolo: " + y);
				((EQuantifier) quantifier).sentence.substituteBy(x, y);
			}
		}
		super.Step5Skolomize(currentDomain);
		((EQuantifier) quantifier).sentence
				.Step5Skolomize((ArrayList<Variable>) clone(currentDomain));
		domain = null;
		quantifier = sentence;

	}

	public static Predicate createNewUnusedPredicate(ArrayList<Variable> domain) {
		String f = "f" + Common.fcounter;
		Common.fcounter++;
		ArrayList<Atom> newDomain = new ArrayList<>();
		for (Variable x : domain)
			newDomain.add((Atom) clone(x));
		return new Predicate(f, newDomain, false);
	}

	public void substituteBy(Variable x, Predicate y) {
		y = new Predicate(y, domain); // append domain to current domains
		if (beforeConnector != null)
			beforeConnector.substituteBy(x, y);
		if (afterConnector != null)
			afterConnector.substituteBy(x, y);
	}

}
