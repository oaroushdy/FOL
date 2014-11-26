package ai.logic.utilities.sentence.term;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.Common;
import ai.logic.utilities.Expression;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.Term;
import ai.logic.utilities.sentence.term.atom.Variable;
import ai.logic.utilities.sentence.term.quantifier.AQuantifier;
import ai.logic.utilities.sentence.term.quantifier.EQuantifier;

public abstract class Quantifier extends Term implements CF {

	public Sentence sentence;
	public ArrayList<Variable> domain;
	protected Expression quantifier;

	public Quantifier(Sentence s, ArrayList<Variable> domain, boolean isNegative) {
		this.sentence = s;
		this.domain = domain;
		this.isNegative = isNegative;
	}

	@Override
	public void negate() {
		if (isNegative) {
			isNegative = false;
			sentence.negate(); // negate sentence
			if (quantifier instanceof AQuantifier)
				quantifier = new EQuantifier(sentence, domain, isNegative);
			else
				quantifier = new AQuantifier(sentence, domain, isNegative);
		}
		sentence.Step3PushNegative();
	}

	@Override
	public void Step1EliminateIFF() {
		sentence.Step1EliminateIFF();
	}

	@Override
	public void Step2RemoveIF() {
		sentence.Step2RemoveIF();
	}

	@Override
	public void Step3PushNegative() {
		if (isNegative) {
			negate();
		}

	}

	@Override
	public void Step4Standardize() {
		for (Variable x : domain) {
			sentence.substituteBy(x, x);
		}
	}

	@Override
	public void Step5Skolomize() {

	}

	@Override
	public void Step6EliminateAQuantifier() {
		if (quantifier.getClass().equals(AQuantifier.class)) {
			quantifier = new Sentence(sentence);// TODO define new constructor
												// consider other data types
												// such as term
		}
	}

	@Override
	public void Step7Expand() {
		quantifier.Step7Expand();
	}

	@Override
	public void Step8Extract() {
		quantifier.Step8Extract();
	}

	@Override
	public String hashKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void substituteBy(Variable x, Variable y) {
		int indexOfX = domain.indexOf(x);
		if (indexOfX != -1) {
			y = Common.createNewUnusedVariable();
			domain.set(indexOfX, y); // replace in domain
			// isNegative is DONT CARE, original isNegative is not replaced
		}
		sentence.substituteBy(x, y); // replace in sentence
	}
}
