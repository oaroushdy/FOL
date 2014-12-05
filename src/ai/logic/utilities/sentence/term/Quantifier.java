package ai.logic.utilities.sentence.term;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.Common;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.Term;
import ai.logic.utilities.sentence.term.atom.Variable;
import ai.logic.utilities.sentence.term.quantifier.AQuantifier;
import ai.logic.utilities.sentence.term.quantifier.EQuantifier;

public abstract class Quantifier extends Term implements CF {

	public Sentence sentence;
	public ArrayList<Variable> domain;
	public Sentence quantifier;

	@SuppressWarnings("unchecked")
	public Quantifier(Sentence s, ArrayList<Variable> domain, boolean isNegative) {
		this.sentence = clone(s);
		this.domain = (ArrayList<Variable>) clone(domain);
		this.isNegative = isNegative;
	}

	public Quantifier(Sentence s, Variable domain, boolean isNegative) {
		this.sentence = clone(s);
		ArrayList<Variable> d = new ArrayList<>();
		d.add((Variable) clone(domain));
		this.domain = d;
		this.isNegative = isNegative;
	}

	public Quantifier(Sentence s) {
		this.sentence = clone(s);
	}

	@Override
	public void negate() {
		isNegative = !isNegative;
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
			isNegative = false;
			sentence.negate(); // negate sentence
			if (quantifier instanceof AQuantifier) {
				quantifier = new EQuantifier(sentence, domain, isNegative);
				sentence = ((EQuantifier) quantifier).sentence;
				isNegative = ((EQuantifier) quantifier).isNegative;
			} else {
				quantifier = new AQuantifier(sentence, domain, isNegative);
				sentence = ((AQuantifier) quantifier).sentence;
				isNegative = ((AQuantifier) quantifier).isNegative;
			}
		}
		sentence.Step3PushNegative();
	}

	@Override
	public void Step4Standardize() {
		for (Variable x : domain) {
			((Quantifier)quantifier).sentence.standardize(x);
			// sentence.substituteBy(x, standard);
		}

	}

	@Override
	public void standardize(Variable x) {
		int posX = ((Quantifier)quantifier).domain.indexOf(x);
		if (posX == -1)
			sentence.standardize(x);
		else { // x conflicts with embedded domain
			Variable var = Common.createNewUnusedVariable();
			((Quantifier)quantifier).domain.set(posX, var);
			((Quantifier)quantifier).sentence.substituteBy(x, var);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void Step5Skolomize(ArrayList<Variable> currentDomain) {
		currentDomain.addAll((ArrayList<Variable>) clone(((Quantifier)quantifier).domain));
	}

	@Override
	public void Step6EliminateAQuantifier() {
		if (quantifier.getClass().equals(AQuantifier.class)) {
			if (quantifier == null)
				return;
			quantifier = sentence;
			domain = null;
		}
		sentence.Step6EliminateAQuantifier();
		quantifier = sentence;

	}

	@Override
	public void Step7Expand() {
		// System.out.println(quantifier);
		// System.out.println(quantifier.getClass().getName());

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
		int indexOfX = ((Quantifier)quantifier).domain.indexOf(x);
		if (indexOfX != -1) {
			y = Common.createNewUnusedVariable();
			((Quantifier)quantifier).domain.set(indexOfX, y); // replace in domain
			// isNegative is DONT CARE, original isNegative is not replaced
		}
		((Quantifier)quantifier).sentence.substituteBy(x, y); // replace in sentence
	}

	@Override
	public String toString() {
		return quantifier.toString();
	}
}
