package ai.logic.utilities;

import ai.logic.utilities.sentence.Connector;
import ai.logic.utilities.sentence.Term;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

/*
 * We assume that a sentence consists of a sentence with at least one 
 * connector
 * 
 * In case the sentence consists only of Ex[Sentence] or Ax[Sentence],
 * then the input is replaced by { Ex[Sentence] OR TRUE }
 */
public class Sentence extends Expression implements CF {

	public Sentence beforeConnector;
	public Sentence afterConnector;
	public Connector connector;

	public boolean isNegative;

	public Sentence(Sentence beforeConnector, Connector connector,
			Sentence afterConnector, boolean isNegative) {
		this.beforeConnector = beforeConnector;
		this.connector = connector;
		this.afterConnector = afterConnector;
		exp = this;
		this.isNegative = isNegative;
	}

	public Sentence() {
	}

	/**
	 * Copy constructor
	 * 
	 * @param sentence
	 *            to copy
	 */
	public Sentence(Sentence sentence) {
		if (sentence.getClass().equals(Sentence.class)) {
			this.beforeConnector = sentence.beforeConnector;
			this.connector = sentence.connector;
			this.afterConnector = sentence.afterConnector;
		}
	}

	@Override
	public void Step1EliminateIFF() {
		Sentence tempLeft, tempRight;
		beforeConnector.Step1EliminateIFF();
		afterConnector.Step1EliminateIFF();

		if (connector.type == Connector.IFF) {
			tempLeft = new Sentence(beforeConnector);
			tempRight = new Sentence(afterConnector);
			afterConnector = new Sentence(tempLeft,
					new Connector(Connector.IF), tempRight, isNegative);
			beforeConnector = new Sentence(tempRight, new Connector(
					Connector.IF), tempLeft, isNegative);
			connector.type = Connector.AND;
		}
	}

	@Override
	public void Step2RemoveIF() {
		beforeConnector.Step2RemoveIF();
		afterConnector.Step2RemoveIF();

		if (connector.type == Connector.IF) {
			beforeConnector.negate(); // negate the left sentence
			connector.type = Connector.OR;
		}
	}

	@Override
	public void Step3PushNegative() {
		if (isNegative) {
			beforeConnector.negate();
			afterConnector.negate();
		}
		beforeConnector.Step3PushNegative();
		connector.negate();
		afterConnector.Step3PushNegative();
	}

	@Override
	public void Step4Standardize() {
		beforeConnector.Step4Standardize();
		afterConnector.Step4Standardize();
	}

	@Override
	public void Step5Skolomize() {
		// TODO Auto-generated method stub
		beforeConnector.Step5Skolomize();
		afterConnector.Step5Skolomize();
	}

	@Override
	public void Step6EliminateAQuantifier() {
		beforeConnector.Step6EliminateAQuantifier();
		afterConnector.Step6EliminateAQuantifier();
	}

	@Override
	public void Step7Expand() {
		if (connector.type == Connector.AND) {
			if (isAtom(beforeConnector) // Atom OR Sentence
					&& connector.type == Connector.OR
					&& !isTerm(afterConnector)) {
				if (afterConnector.connector.type == Connector.AND)
					convertSentence((Atom) beforeConnector, afterConnector,
							Common.EXPAND_OR);

			} else if (isAtom(afterConnector) && connector.type == Connector.OR
					&& !isTerm(beforeConnector)) {
				if (beforeConnector.connector.type == Connector.AND)
					convertSentence((Atom) afterConnector, beforeConnector,
							Common.EXPAND_OR);
			}
		} else if (connector.type == Connector.OR) {
			if (isAtom(beforeConnector) // Atom OR Sentence
					&& connector.type == Connector.AND
					&& !isTerm(afterConnector)) {
				if (afterConnector.connector.type == Connector.OR)
					convertSentence((Atom) beforeConnector, afterConnector,
							Common.EXPAND_AND);

			} else if (isAtom(afterConnector)
					&& connector.type == Connector.AND
					&& !isTerm(beforeConnector)) {
				if (beforeConnector.connector.type == Connector.OR)
					convertSentence((Atom) afterConnector, beforeConnector,
							Common.EXPAND_AND);
			}
		}
		beforeConnector.Step7Expand();
		afterConnector.Step7Expand();
	}

	private void convertSentence(Atom left, Sentence right, int expandType) {
		Sentence tempLeft = new Sentence(right.beforeConnector);
		Sentence tempRight = new Sentence(right.afterConnector);
		switch (expandType) {
		case Common.EXPAND_AND: // TODO should copy object left!
			beforeConnector = new Sentence(left, new Connector(Connector.AND),
					tempLeft, false); // isNegative = false since we already
										// expanded the negative
			afterConnector = new Sentence(left, new Connector(Connector.AND),
					tempRight, false);
			connector.type = Connector.OR;
			break;
		case Common.EXPAND_OR: // TODO should copy object left!
			beforeConnector = new Sentence(left, new Connector(Connector.OR),
					tempLeft, false); // isNegative = false since we already
										// expanded the negative
			afterConnector = new Sentence(left, new Connector(Connector.OR),
					tempRight, false);
			connector.type = Connector.AND;
			break;
		}
	}

	public static boolean isTerm(Sentence s) {
		return s instanceof Term;
	}

	public static boolean isAtom(Sentence s) {
		return (s instanceof Atom);
	}

	@Override
	public void Step8Extract() {
		// TODO Auto-generated method stub

	}

	@Override
	public String hashKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void negate() {
		isNegative = !isNegative;
	}

	public String toString() {
		return beforeConnector.toString() + connector.toString()
				+ afterConnector.toString();
	}

	public void substituteBy(Variable x, Variable y) {
		beforeConnector.substituteBy(x, y);
		afterConnector.substituteBy(x, y);
	}

	public void substituteBy(Variable x, Predicate y) {
		beforeConnector.substituteBy(x, y);
		afterConnector.substituteBy(x, y);
	}

}
